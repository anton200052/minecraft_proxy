package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponsePacketV760 extends StatusResponsePacket {
    public StatusResponsePacketV760(int packetId, MinecraftServerInfo minecraftServerInfo) {
        super(packetId, minecraftServerInfo);
    }
}
