package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class SystemChatMessagePacketV754 extends SystemChatMessagePacket {
    private byte position;
    private UUID sender;

    public SystemChatMessagePacketV754(int packetId, ChatMessage chatMessage, byte position, UUID sender) {
        super(packetId, chatMessage);
        this.position = position;
        this.sender = sender;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] baseClassData = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] positionByte = packetDataCodec.encodeByte(this.position);
        byte[] senderUUID = packetDataCodec.encodeUUID(this.sender);

        return byteArrayHelper.merge(baseClassData, positionByte, senderUUID);
    }
}
