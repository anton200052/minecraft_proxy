package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatCommandPacketV760 extends ChatCommandPacket {
    public ChatCommandPacketV760(int packetId, String command, long timestamp, long salt, byte[] remainingBytes) {
        super(packetId, command, timestamp, salt, remainingBytes);
    }
}
