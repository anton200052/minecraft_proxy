package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacketV754;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class MovePlayerPosRotPacketHandlerV754 extends MovePlayerPosRotPacketHandler {
    public MovePlayerPosRotPacketHandlerV754(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    protected void sendSpecificSyncPacket(ProxyConnection proxyConnection, int compressionThreshold, double x, double y, double z, float yaw, float pitch) {
        SynchronizePlayerPosPacketV754 syncPacket = new SynchronizePlayerPosPacketV754(
                52,
                x,
                y,
                z,
                yaw,
                pitch,
                (byte) 0,
                1
        );

        extraPacketSender.sendExtraPacketToMirrorClient(
                proxyConnection,
                syncPacket,
                compressionThreshold
                                                       );
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return MovePlayerPosRotPacketV754.class;
    }
}
