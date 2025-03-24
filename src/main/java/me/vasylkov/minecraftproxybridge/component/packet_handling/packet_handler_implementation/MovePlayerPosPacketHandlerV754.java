package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacketV754;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class MovePlayerPosPacketHandlerV754 extends MovePlayerPosPacketHandler {
    public MovePlayerPosPacketHandlerV754(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    protected void sendSpecificSyncPacket(ProxyConnection proxyConnection, int compressionThreshold, double x, double y, double z) {
        SynchronizePlayerPosPacketV754 syncPacket = new SynchronizePlayerPosPacketV754(
                52,
                x,
                y,
                z,
                0.0F,
                0.0F,
                (byte) 24,
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
        return MovePlayerPosPacketV754.class;
    }
}
