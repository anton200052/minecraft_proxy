package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.DisconnectLoginPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginStartPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.MainProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;

import java.util.List;
import java.util.UUID;

public abstract class LoginStartPacketHandler implements PacketHandler {
    protected final ExtraPacketSender extraPacketSender;
    protected final ConnectedProxyConnections connectedProxyConnections;

    protected LoginStartPacketHandler(ExtraPacketSender extraPacketSender, ConnectedProxyConnections connectedProxyConnections) {
        this.extraPacketSender = extraPacketSender;
        this.connectedProxyConnections = connectedProxyConnections;
    }

    @Override
    public Packet handlePacket(ProxyConnection mirrorProxyConnection, Packet packet, ClientType clientType) {
        LoginStartPacket loginStartPacket = (LoginStartPacket) packet;

        if (clientType == ClientType.MIRROR) {
            String username = loginStartPacket.getUsername();
            if (connectedProxyConnections.containsProxyConnection(username)) {
                ProxyConnection mainProxyConnection = connectedProxyConnections.getProxyConnection(username);

                sendDisconnectPacketIfVersionDiffers(mainProxyConnection.getServerData().getServerVersion(), mirrorProxyConnection.getServerData().getServerVersion(), mirrorProxyConnection);
                mergeProxyConnections(mainProxyConnection, mirrorProxyConnection);

                UUID mainClientUUID = mirrorProxyConnection.getMainProxyClient().getUuid();

                sendSpecificLoginPackets(mirrorProxyConnection, mirrorProxyConnection.getServerData().getCompressionThreshold(), mainClientUUID, username);

                mirrorProxyConnection.getMirrorProxyClient().setPacketState(PacketState.PLAY);
            }
        }

        return loginStartPacket;
    }

    private void mergeProxyConnections(ProxyConnection mainProxyConnection, ProxyConnection mirrorProxyConnection) {
        MainProxyClient mainProxyClient = mainProxyConnection.getMainProxyClient();
        MirrorProxyClient mirrorProxyClient = mirrorProxyConnection.getMirrorProxyClient();

        mainProxyConnection.setMirrorProxyClient(mirrorProxyClient);

        mirrorProxyConnection.setMainProxyClient(mainProxyClient);
        mirrorProxyConnection.setServerData(mainProxyConnection.getServerData());
    }

    private void sendDisconnectPacketIfVersionDiffers(ServerVersion mainClientVersion, ServerVersion mirrorClientVersion, ProxyConnection proxyConnection) {
        if (mirrorClientVersion != mainClientVersion) {
            DisconnectLoginPacket disconnectLoginPacket = new DisconnectLoginPacket(new ChatMessage(List.of(new ChatMessage.Extra(true, false, false, false, false, "white", "[Proxy] Для подключения к зеркальному прокси вы должны иметь версию и ник основного клиента.")), ""));
            extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, disconnectLoginPacket, 0);
        }
    }


    protected abstract void sendSpecificLoginPackets(ProxyConnection proxyConnection, int compressionThreshold, UUID uuid, String username);
}
