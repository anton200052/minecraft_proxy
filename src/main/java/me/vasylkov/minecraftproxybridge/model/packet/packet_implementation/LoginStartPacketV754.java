package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginStartPacketV754 extends LoginStartPacket {
    public LoginStartPacketV754(int packetId, String username) {
        super(packetId, username);
    }
}
