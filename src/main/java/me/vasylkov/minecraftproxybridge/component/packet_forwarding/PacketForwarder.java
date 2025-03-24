package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools.PacketHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.*;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.*;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Component
@RequiredArgsConstructor
public class PacketForwarder {
    private final Logger logger;
    private final ProxyConfiguration proxyConfiguration;
    private final PacketDataCodec packetDataCodec;
    private final PacketHelper packetHelper;
    private final PacketHandlingDispatcher packetHandlingDispatcher;
    private final PacketParserDispatcher packetParserDispatcher;
    private final ZlibCompressor zlibCompressor;
    private final PacketEncoder packetEncoder;
    private final PacketRawDataSender packetRawDataSender;
    private final ConnectedProxyConnections connectedProxyConnections;

    @Async
    public void forwardDataFromMainClient(ProxyConnection proxyConnection) {
        try (Socket clientSocket = proxyConnection.getMainProxyClient().getSocket()) {

            InputStream input = clientSocket.getInputStream();

            while (proxyConfiguration.isEnabled() && !clientSocket.isClosed() && !proxyConnection.getServerData().getSocket().isClosed()) {
                byte[] packetData = processPacket(input, PacketDirection.CLIENT_TO_SERVER, ClientType.MAIN, proxyConnection);
                if (packetData != null && packetData.length > 0) {
                    packetRawDataSender.sendPacketDataToServer(proxyConnection, packetData);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connectedProxyConnections.removeProxyConnection(proxyConnection.getMainProxyClient().getUserName());
            MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();

            if (mirrorProxyClient != null) {
                mirrorProxyClient.setForwardingFromMirrorProxyAllowed(true);
            }
        }
    }

    @Async
    public void forwardDataFromMirrorClient(ProxyConnection proxyConnection) {
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
        try (Socket clientSocket = mirrorProxyClient.getSocket()) {

            InputStream input = clientSocket.getInputStream();

            while (proxyConfiguration.isEnabled() && !clientSocket.isClosed() && !proxyConnection.getServerData().getSocket().isClosed()) {
                byte[] packetData = processPacket(input, PacketDirection.CLIENT_TO_SERVER, ClientType.MIRROR, proxyConnection);
                if (packetData != null && packetData.length > 0 && mirrorProxyClient.isForwardingFromMirrorProxyAllowed()) {
                    packetRawDataSender.sendPacketDataToServer(proxyConnection, packetData);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            proxyConnection.setMirrorProxyClient(null);
        }
    }


    @Async
    public void forwardDataToMirrorClientUntilMerge(ProxyConnection proxyConnection) {
        try (Socket serverSocket = proxyConnection.getServerData().getSocket()) {
            InputStream serverInput = serverSocket.getInputStream();
            while (proxyConfiguration.isEnabled()) {
                byte[] packetData = processPacket(serverInput, PacketDirection.SERVER_TO_CLIENT, ClientType.MIRROR, proxyConnection);
                if (packetData == null || packetData.length == 0) {
                    continue;
                }

                MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
                if (mirrorProxyClient == null || !mirrorProxyClient.isForwardingToMirrorProxyAllowed() || mirrorProxyClient.getSocket().isClosed()) {
                    return;
                }
                packetRawDataSender.sendPacketDataToMirrorClient(proxyConnection, packetData);
            }
        }
        catch (Exception e) {
            logger.warn("Временная пересылка пакетов от сервера зеркальному клиенту отключена с причиной: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    public void forwardDataToClients(ProxyConnection proxyConnection) {
        try (Socket serverSocket = proxyConnection.getServerData().getSocket()) {
            InputStream serverInput = serverSocket.getInputStream();
            while (proxyConfiguration.isEnabled()) {
                byte[] packetData = processPacket(serverInput, PacketDirection.SERVER_TO_CLIENT, ClientType.MAIN, proxyConnection);
                if (packetData == null || packetData.length == 0) {
                    continue;
                }

                MainProxyClient mainProxyClient = proxyConnection.getMainProxyClient();
                if (mainProxyClient != null && !mainProxyClient.getSocket().isClosed()) {
                    packetRawDataSender.sendPacketDataToMainClient(proxyConnection, packetData);
                }

                MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
                if (mirrorProxyClient != null && mirrorProxyClient.isForwardingToMirrorProxyAllowed() && !mirrorProxyClient.getSocket().isClosed()) {
                    packetRawDataSender.sendPacketDataToMirrorClient(proxyConnection, packetData);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] processPacket(InputStream input, PacketDirection packetDirection, ClientType clientType, ProxyConnection proxyConnection) throws IOException {
        int packetLength = packetDataCodec.readVarInt(input);
        if (packetLength < 0) {
            return new byte[0];
        }
        int dataLength = 0;
        int compressionThreshold = getCompressionThreshold(proxyConnection);
        if (compressionThreshold > 0) {
            dataLength = packetDataCodec.readVarInt(input);
            packetLength -= packetDataCodec.encodeVarInt(dataLength).length;
        }
        byte[] buffer = packetHelper.readRemainingPacketData(input, packetLength);
        if (dataLength > 0) {
            buffer = zlibCompressor.decompressZlib(buffer, dataLength);
        }

        ServerVersion serverVersion = getServerVersion(proxyConnection);
        PacketState state = getPacketState(proxyConnection, clientType);
        Packet parsedPacket = packetParserDispatcher.parsePacket(serverVersion, state, packetDirection, buffer);
        Packet handledPacket = packetHandlingDispatcher.handlePacket(proxyConnection, clientType, parsedPacket);

        if (handledPacket == null) {
            return new byte[0];
        }

        byte[] packetData = packetEncoder.encodePacket(handledPacket, compressionThreshold);
        packetHelper.printPacketData(packetData, packetDirection, state, clientType);
        return packetData;
    }

    private int getCompressionThreshold(ProxyConnection proxyConnection) {
        ServerData serverData = proxyConnection.getServerData();
        int compressionThreshold = 0;
        if (serverData != null) {
            compressionThreshold = serverData.getCompressionThreshold();
        }
        return compressionThreshold;
    }

    private PacketState getPacketState(ProxyConnection proxyConnection, ClientType clientType) {
        return clientType == ClientType.MAIN ? proxyConnection.getMainProxyClient().getPacketState() : proxyConnection.getMirrorProxyClient().getPacketState();
    }

    private ServerVersion getServerVersion(ProxyConnection proxyConnection) {
        ServerData serverData = proxyConnection.getServerData();
        ServerVersion serverVersion = ServerVersion.UNKNOWN;
        if (serverData != null) {
            serverVersion = serverData.getServerVersion();
        }
        return serverVersion;
    }
}
