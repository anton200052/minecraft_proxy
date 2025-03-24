package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacketV760;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class MovePlayerPosPacketHandlerV760 extends MovePlayerPosPacketHandler {
    public MovePlayerPosPacketHandlerV760(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    protected void sendSpecificSyncPacket(ProxyConnection proxyConnection, int compressionThreshold, double x, double y, double z) {
        SynchronizePlayerPosPacketV760 syncPacket = new SynchronizePlayerPosPacketV760(
                57,
                x,
                y,
                z,
                0.0F,
                0.0F,
                (byte) 24,
                1,
                false
        );

        extraPacketSender.sendExtraPacketToMirrorClient(
                proxyConnection,
                syncPacket,
                compressionThreshold
                                                       );
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return MovePlayerPosPacketV760.class;
    }
}
