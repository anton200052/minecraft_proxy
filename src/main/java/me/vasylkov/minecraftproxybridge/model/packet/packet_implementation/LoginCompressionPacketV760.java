package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCompressionPacketV760 extends LoginCompressionPacket {
    public LoginCompressionPacketV760(int packetId, int compression) {
        super(packetId, compression);
    }
}
