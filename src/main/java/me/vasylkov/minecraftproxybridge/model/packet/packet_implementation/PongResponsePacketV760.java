package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PongResponsePacketV760 extends PongResponsePacket {
    public PongResponsePacketV760(int packetId, long timestamp) {
        super(packetId, timestamp);
    }
}

