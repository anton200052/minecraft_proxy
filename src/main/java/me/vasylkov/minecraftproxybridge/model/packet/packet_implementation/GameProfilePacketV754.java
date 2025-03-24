package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GameProfilePacketV754 extends GameProfilePacket {
    public GameProfilePacketV754(int packetId, UUID uuid, String username) {
        super(packetId, uuid, username);
    }
}
