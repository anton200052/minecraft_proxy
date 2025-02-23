package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools.PacketHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketEncoder;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ZlibCompressor;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.component.proxy.MirroredProxyConnector;
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
            while (proxyConfiguration.isEnabled()) {
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
            connectedProxyConnections.removeProxyConnection(proxyConnection.getMainProxyClient().getHostAddress());
            MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
            proxyConnection.setMainProxyClient(null);

            if (mirrorProxyClient != null) {
                mirrorProxyClient.setForwardingFromMirrorProxyAllowed(true);
            }
        }
    }

    @Async
    public void forwardDataFromMirrorClient(ProxyConnection proxyConnection) {
        try (Socket clientSocket = proxyConnection.getMirrorProxyClient().getSocket()) {
            InputStream input = clientSocket.getInputStream();
            while (proxyConfiguration.isEnabled()) {
                byte[] packetData = processPacket(input, PacketDirection.CLIENT_TO_SERVER, ClientType.MIRROR, proxyConnection);

                MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
                if (packetData != null && packetData.length > 0 && mirrorProxyClient != null && mirrorProxyClient.isForwardingFromMirrorProxyAllowed()) {
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
        int compressionThreshold = getCompressionThreshold(proxyConnection, clientType);
        if (compressionThreshold > 0) {
            dataLength = packetDataCodec.readVarInt(input);
            packetLength -= packetDataCodec.encodeVarInt(dataLength).length;
        }
        byte[] buffer = packetHelper.readRemainingPacketData(input, packetLength);
        if (dataLength > 0) {
            buffer = zlibCompressor.decompressZlib(buffer, dataLength);
        }
        PacketState state = getPacketState(proxyConnection, clientType);
        Packet parsedPacket = packetParserDispatcher.parsePacket(state, packetDirection, buffer);
        Packet handledPacket = packetHandlingDispatcher.handlePacket(proxyConnection, clientType, parsedPacket);
        if (handledPacket == null) {
            return new byte[0];
        }
        byte[] packetData = packetEncoder.encodePacket(handledPacket, compressionThreshold);
        packetHelper.printPacketData(packetData, packetDirection, state, clientType);
        return packetData;
    }

    private int getCompressionThreshold(ProxyConnection proxyConnection, ClientType clientType) {
        return clientType == ClientType.MAIN ? proxyConnection.getMainProxyClient().getCompressionThreshold() : proxyConnection.getMirrorProxyClient().getCompressionThreshold();
    }

    private PacketState getPacketState(ProxyConnection proxyConnection, ClientType clientType) {
        return clientType == ClientType.MAIN ? proxyConnection.getMainProxyClient().getPacketState() : proxyConnection.getMirrorProxyClient().getPacketState();
    }
}
