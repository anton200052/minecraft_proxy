package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PongResponsePacketV754 extends PongResponsePacket {
    public PongResponsePacketV754(int packetId, long timestamp) {
        super(packetId, timestamp);
    }
}
