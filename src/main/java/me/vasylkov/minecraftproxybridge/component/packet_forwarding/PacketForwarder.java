package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools.PacketHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketEncoder;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ZlibCompressor;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyClientsStorage;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
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
    private final ConnectedProxyClientsStorage connectedProxyClientsStorage;

    @Async
    public void forwardDataFromMainClient(ProxyClient proxyClient) {
        ProxyClient.ClientConnection clientConnection = proxyClient.getConnection();
        try (Socket clientSocket = clientConnection.getMainClientSocket()) {
            InputStream input = clientSocket.getInputStream();

            while (proxyConfiguration.isEnabled()) {
                byte[] packetData = processPacket(input, PacketDirection.CLIENT_TO_SERVER, ClientType.MAIN, proxyClient);

                if (packetData != null) {
                    packetRawDataSender.sendPacketDataToServer(clientConnection, packetData);
                }
            }
        }
        catch (Exception e) {
            logger.error("Ошибка обработки пакета (proxy -> server): {}", e.getMessage());
        }
        finally {
            connectedProxyClientsStorage.removeProxyClient(proxyClient.getData().getHostAddress());
            proxyClient.getState().setForwardingFromMirrorProxyAllowed(true);
            proxyClient.getConnection().setMainClientSocket(null);
        }
    }

    @Async
    public void forwardDataFromMirrorClient(ProxyClient proxyClient) {
        ProxyClient.ClientConnection clientConnection = proxyClient.getConnection();
        try (Socket clientSocket = clientConnection.getMirrorClientSocket()) {
            InputStream input = clientSocket.getInputStream();

            while (proxyConfiguration.isEnabled()) {
                byte[] packetData = processPacket(input, PacketDirection.CLIENT_TO_SERVER, ClientType.MIRROR, proxyClient);

                if (packetData != null && proxyClient.getState().isForwardingFromMirrorProxyAllowed()) {
                    packetRawDataSender.sendPacketDataToServer(clientConnection, packetData);
                }
            }
        }
        catch (Exception e) {
            logger.error("Ошибка обработки пакета (mirror proxy -> server): {}", e.getMessage());
        }
        finally {
            proxyClient.getConnection().setMirrorClientSocket(null);
            proxyClient.getState().setForwardingFromMirrorProxyAllowed(false);
            proxyClient.getState().setForwardingToMirrorProxyAllowed(false);
            proxyClient.getState().setMirrorProxyState(PacketState.HANDSHAKE);
        }
    }

    @Async
    public void forwardDataToClients(ProxyClient proxyClient) {
        ProxyClient.ClientConnection connection = proxyClient.getConnection();
        ProxyClient.ClientState state = proxyClient.getState();

        try (Socket serverSocket = connection.getServerSocket()) {
            InputStream serverInput = serverSocket.getInputStream();

            while (proxyConfiguration.isEnabled()) {

                byte[] packetData = processPacket(serverInput, PacketDirection.SERVER_TO_CLIENT, ClientType.MAIN, proxyClient);
                if (packetData == null || packetData.length == 0) {
                    continue;
                }

                if (proxyClient.getConnection().getMainClientSocket() != null) {
                    packetRawDataSender.sendPacketDataToMainClient(connection, packetData);
                }

                if (state.isForwardingToMirrorProxyAllowed()) {
                    Socket mirrorSocket = connection.getMirrorClientSocket();
                    if (mirrorSocket != null && !mirrorSocket.isClosed()) {
                        packetRawDataSender.sendPacketDataToMirrorClient(connection, packetData);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("Ошибка обработки пакета (server -> proxies): {}", e.getMessage());
        }
    }

    private byte[] processPacket(InputStream input, PacketDirection packetDirection, ClientType clientType, ProxyClient proxyClient) throws IOException {
        int packetLength = packetDataCodec.readVarInt(input);
        int dataLength = 0;

        int compressionThreshold = getCompressionThreshold(proxyClient, clientType);
        if (compressionThreshold > 0) {
            dataLength = packetDataCodec.readVarInt(input);
            packetLength -= packetDataCodec.encodeVarInt(dataLength).length;
        }

        byte[] buffer = packetHelper.readRemainingPacketData(input, packetLength);
        if (dataLength > 0) {
            buffer = zlibCompressor.decompressZlib(buffer, dataLength);
        }

        PacketState state = getPacketState(proxyClient, clientType);
        Packet parsedPacket = packetParserDispatcher.parsePacket(state, packetDirection, buffer);
        Packet handledPacket  = packetHandlingDispatcher.handlePacket(proxyClient, clientType, parsedPacket);
        if (handledPacket != null) {
            byte[] packetData = packetEncoder.encodePacket(handledPacket, compressionThreshold);
            packetHelper.printPacketData(packetData, packetDirection, state, clientType);
            return packetData;
        }
        return new byte[0];
    }

    private int getCompressionThreshold(ProxyClient proxyClient, ClientType clientType) {
        return clientType == ClientType.MAIN ?
                proxyClient.getData().getMainClientCompressionThreshold() :
                proxyClient.getData().getMirrorClientCompressionThreshold();
    }

    private PacketState getPacketState(ProxyClient proxyClient, ClientType clientType) {
        return clientType == ClientType.MAIN ?
                proxyClient.getState().getMainProxyState() :
                proxyClient.getState().getMirrorProxyState();
    }
}