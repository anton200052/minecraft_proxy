package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PingRequestPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PongResponsePacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PingRequestPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        PingRequestPacket pingRequestPacket = (PingRequestPacket) packet;

        if (clientType == ClientType.MIRROR) {
            PongResponsePacket pongResponsePacket = new PongResponsePacket(1, pingRequestPacket.getTimestamp());
            extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, pongResponsePacket, proxyConnection.getMirrorProxyClient().getCompressionThreshold());
        }

        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return PingRequestPacket.class;
    }
}
