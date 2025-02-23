package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class SystemChatMessagePacket extends Packet {
    private ChatMessage chatMessage;
    private boolean overlay;

    public SystemChatMessagePacket(int packetId, ChatMessage chatMessage, boolean overlay) {
        super(packetId);
        this.chatMessage = chatMessage;
        this.overlay = overlay;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(this.chatMessage);

        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] messageLengthVarInt = packetDataCodec.encodeVarInt(messageBytes.length);
        byte[] messageString = packetDataCodec.encodeString(message);
        byte[] overlayBoolean = packetDataCodec.encodeBoolean(this.overlay);


        return byteArrayHelper.merge(packetIdVarInt, messageLengthVarInt, messageString, overlayBoolean);
    }

}
