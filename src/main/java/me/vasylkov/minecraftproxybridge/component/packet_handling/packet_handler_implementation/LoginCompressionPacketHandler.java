package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginCompressionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.model.proxy.ServerData;
import org.springframework.stereotype.Component;

@Component
public class LoginCompressionPacketHandler implements PacketHandler {
    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        LoginCompressionPacket loginCompressionPacket = (LoginCompressionPacket) packet;
        ServerData serverData = proxyConnection.getServerData();

        updateServerCompressionThreshold(serverData, loginCompressionPacket.getCompression());

        return loginCompressionPacket;
    }

    private void updateServerCompressionThreshold(ServerData serverData, int compressionThreshold) {
        serverData.setCompressionThreshold(compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginCompressionPacket.class;
    }
}
