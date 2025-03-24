package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovePlayerPosPacketV760 extends MovePlayerPosPacket {
    public MovePlayerPosPacketV760(int packetId, double x, double y, double z, boolean onGround) {
        super(packetId, x, y, z, onGround);
    }
}