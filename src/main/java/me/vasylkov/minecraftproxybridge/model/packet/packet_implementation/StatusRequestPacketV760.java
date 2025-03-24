package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequestPacketV760 extends StatusRequestPacket {

    public StatusRequestPacketV760(int packetId, byte[] remainingBytes) {
        super(packetId, remainingBytes);
    }
}
