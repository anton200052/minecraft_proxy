package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PingRequestPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PongResponsePacketV754;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class PingRequestPacketHandlerV754 extends PingRequestPacketHandler {
    public PingRequestPacketHandlerV754(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    public void sendSpecificPongPacket(ProxyConnection proxyConnection, int compressionThreshold, long timestamp) {
        PongResponsePacketV754 pongResponsePacket = new PongResponsePacketV754(1, timestamp);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, pongResponsePacket, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return PingRequestPacketV754.class;
    }
}
