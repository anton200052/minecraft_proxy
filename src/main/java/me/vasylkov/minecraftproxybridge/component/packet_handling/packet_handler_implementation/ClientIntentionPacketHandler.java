package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.DisconnectLoginPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import me.vasylkov.minecraftproxybridge.model.proxy.*;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientIntentionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientIntentionPacketHandler implements PacketHandler{
    private final ProxyConfiguration proxyConfiguration;
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        ClientIntentionPacket clientIntentionPacket = (ClientIntentionPacket) packet;

        updateLocalAddressToServerAddress(clientIntentionPacket);

        int nextState = clientIntentionPacket.getNextState();
        PacketState newState = (nextState == 1) ? PacketState.STATUS : PacketState.LOGIN;

        MainProxyClient mainProxyClient = proxyConnection.getMainProxyClient();
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
        ServerData serverData = proxyConnection.getServerData();
        int compressionThreshold = serverData.getCompressionThreshold();

        ServerVersion serverVersion = null;
        try {
            serverVersion = ServerVersion.fromProtocolVersion(clientIntentionPacket.getProtocolVersion());
        }
        catch (IllegalArgumentException e) {
            sendDisconnectPacket(proxyConnection, clientType, compressionThreshold);
        }

        updateClientStateAndServerVersion(clientType, mainProxyClient, mirrorProxyClient, serverData, newState, serverVersion);

        return clientIntentionPacket;
    }

    private void updateClientStateAndServerVersion(ClientType clientType, MainProxyClient mainProxyClient, MirrorProxyClient mirrorProxyClient, ServerData serverData, PacketState newState, ServerVersion newVersion) {
        if (clientType == ClientType.MAIN) {
            mainProxyClient.setPacketState(newState);
        } else {
            mirrorProxyClient.setPacketState(newState);
            updateMirrorClientForwardingFlag(mirrorProxyClient, newState);
        }
        serverData.setServerVersion(newVersion);
    }

    private void updateMirrorClientForwardingFlag(MirrorProxyClient mirrorProxyClient, PacketState newState) {
        if (newState == PacketState.LOGIN) {
            mirrorProxyClient.setForwardingToMirrorProxyAllowed(false);
            mirrorProxyClient.setForwardingFromMirrorProxyAllowed(false);
        }
    }

    private void updateLocalAddressToServerAddress(ClientIntentionPacket clientIntentionPacket) {
        clientIntentionPacket.setServerAddress(proxyConfiguration.getProxyInfo().getTargetServerAddress());
    }


    private void sendDisconnectPacket(ProxyConnection proxyConnection, ClientType clientType, int compressionThreshold) {
        DisconnectLoginPacket disconnectLoginPacket = new DisconnectLoginPacket(new ChatMessage(List.of(new ChatMessage.Extra(true, false, false, false, false, "white", "[Proxy] Неподдерживаемая версия. Используйте 1.16.5 или 1.19.2")), ""));
        if (clientType == ClientType.MAIN) {
            extraPacketSender.sendExtraPacketToMainClient(proxyConnection, disconnectLoginPacket, compressionThreshold);
        }
        else {
            extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, disconnectLoginPacket, compressionThreshold);
        }
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientIntentionPacket.class;
    }
}
