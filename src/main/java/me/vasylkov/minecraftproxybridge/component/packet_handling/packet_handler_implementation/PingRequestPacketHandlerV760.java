package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.*;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class PingRequestPacketHandlerV760 extends PingRequestPacketHandler {
    public PingRequestPacketHandlerV760(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    public void sendSpecificPongPacket(ProxyConnection proxyConnection, int compressionThreshold, long timestamp) {
        PongResponsePacketV760 pongResponsePacket = new PongResponsePacketV760(1, timestamp);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, pongResponsePacket, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return PingRequestPacketV760.class;
    }
}
