package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCompressionPacketV754 extends LoginCompressionPacket {

    public LoginCompressionPacketV754(int packetId, int compression) {
        super(packetId, compression);
    }
}
