package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientChatPacketV754 extends ClientChatPacket {
    public ClientChatPacketV754(int packetId, String message) {
        super(packetId, message);
    }
}
