package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.*;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoginStartPacketHandlerV754 extends LoginStartPacketHandler {
    public LoginStartPacketHandlerV754(ExtraPacketSender extraPacketSender, ConnectedProxyConnections connectedProxyConnections) {
        super(extraPacketSender, connectedProxyConnections);
    }

    @Override
    protected void sendSpecificLoginPackets(ProxyConnection proxyConnection, int compressionThreshold, UUID uuid, String username) {
        LoginCompressionPacketV754 loginCompressionPacket = new LoginCompressionPacketV754(3, compressionThreshold);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, loginCompressionPacket, 0);

        GameProfilePacketV754 gameProfilePacketV754 = new GameProfilePacketV754(2, uuid, username);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, gameProfilePacketV754, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginStartPacketV754.class;
    }
}
