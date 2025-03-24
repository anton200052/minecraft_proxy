package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PingRequestPacketV754 extends PingRequestPacket {
    public PingRequestPacketV754(int packetId, long timestamp) {
        super(packetId, timestamp);
    }
}

