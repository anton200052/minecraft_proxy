package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PingRequestPacketV760 extends PingRequestPacket {
    public PingRequestPacketV760(int packetId, long timestamp) {
        super(packetId, timestamp);
    }
}
