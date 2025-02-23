package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class ClientChatPacket extends Packet {
    private String message;
    private long timestamp;
    private long salt;
    private byte[] remainingBytes;


    public ClientChatPacket(int packetId, String message, long timestamp, long salt, byte[] remainingBytes) {
        super(packetId);
        this.message = message;
        this.timestamp = timestamp;
        this.salt = salt;
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] messageBytes = this.message.getBytes(StandardCharsets.UTF_8);
        byte[] messageLengthVarInt = packetDataCodec.encodeVarInt(messageBytes.length);
        byte[] messageString = packetDataCodec.encodeString(this.message);
        byte[] timeStampLong = packetDataCodec.encodeLong(this.timestamp);
        byte[] saltLong = packetDataCodec.encodeLong(this.salt);

        return byteArrayHelper.merge(packetIdVarInt, messageLengthVarInt, messageString, timeStampLong, saltLong, this.remainingBytes);
    }
}
