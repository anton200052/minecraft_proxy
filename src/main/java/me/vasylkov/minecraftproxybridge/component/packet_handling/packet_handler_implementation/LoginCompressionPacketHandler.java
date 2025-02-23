package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginCompressionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

@Component
public class LoginCompressionPacketHandler implements PacketHandler {

    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        LoginCompressionPacket loginCompressionPacket = (LoginCompressionPacket) packet;
        proxyClient.getData().setMainClientCompressionThreshold(loginCompressionPacket.getCompression());
        return loginCompressionPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginCompressionPacket.class;
    }


}
