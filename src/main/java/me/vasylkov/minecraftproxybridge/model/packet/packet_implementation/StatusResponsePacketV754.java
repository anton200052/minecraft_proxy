package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponsePacketV754 extends StatusResponsePacket {
    public StatusResponsePacketV754(int packetId, MinecraftServerInfo minecraftServerInfo) {
        super(packetId, minecraftServerInfo);
    }
}
