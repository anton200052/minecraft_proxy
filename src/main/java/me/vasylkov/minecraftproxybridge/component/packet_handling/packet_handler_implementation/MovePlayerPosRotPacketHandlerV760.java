package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacketV760;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class MovePlayerPosRotPacketHandlerV760 extends MovePlayerPosRotPacketHandler {
    public MovePlayerPosRotPacketHandlerV760(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    protected void sendSpecificSyncPacket(ProxyConnection proxyConnection, int compressionThreshold, double x, double y, double z, float yaw, float pitch) {
        SynchronizePlayerPosPacketV760 syncPacket = new SynchronizePlayerPosPacketV760(
                57,
                x,
                y,
                z,
                yaw,
                pitch,
                (byte) 0,
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
        return MovePlayerPosRotPacketV760.class;
    }
}
