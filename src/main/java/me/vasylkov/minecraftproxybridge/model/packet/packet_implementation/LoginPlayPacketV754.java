package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPlayPacketV754 extends LoginPlayPacket {
    public LoginPlayPacketV754(int packetId, byte[] bytes) {
        super(packetId, bytes);
    }
}