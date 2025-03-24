package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SynchronizePlayerPosPacketV754 extends SynchronizePlayerPosPacket {
    public SynchronizePlayerPosPacketV754(int packetId, double x, double y, double z, float yaw, float pitch, byte relativeFlags, int teleportId) {
        super(packetId, x, y, z, yaw, pitch, relativeFlags, teleportId);
    }
}
