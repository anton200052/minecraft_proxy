package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovePlayerPosRotPacketV754 extends MovePlayerPosRotPacket {
    public MovePlayerPosRotPacketV754(int packetId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        super(packetId, x, y, z, yaw, pitch, onGround);
    }
}
