package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.*;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoginStartPacketHandlerV760 extends LoginStartPacketHandler {
    public LoginStartPacketHandlerV760(ExtraPacketSender extraPacketSender, ConnectedProxyConnections connectedProxyConnections) {
        super(extraPacketSender, connectedProxyConnections);
    }

    @Override
    protected void sendSpecificLoginPackets(ProxyConnection proxyConnection, int compressionThreshold, UUID uuid, String username) {
        LoginCompressionPacketV760 loginCompressionPacket = new LoginCompressionPacketV760(3, compressionThreshold);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, loginCompressionPacket, 0);

        GameProfilePacketV760 gameProfilePacketV760 = new GameProfilePacketV760(2, uuid, username, new byte[]{0});
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, gameProfilePacketV760, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginStartPacketV760.class;
    }
}
