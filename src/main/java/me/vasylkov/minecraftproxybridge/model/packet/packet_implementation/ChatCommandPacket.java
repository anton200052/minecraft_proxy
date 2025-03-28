package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public abstract class ChatCommandPacket extends Packet {
    private String command;
    private long timestamp;
    private long salt;
    private byte[] remainingBytes;

    protected ChatCommandPacket(int packetId, String command, long timestamp, long salt, byte[] remainingBytes) {
        super(packetId);
        this.command = command;
        this.timestamp = timestamp;
        this.salt = salt;
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] commandBytes = this.command.getBytes(StandardCharsets.UTF_8);
        byte[] commandLengthVarInt = packetDataCodec.encodeVarInt(commandBytes.length);
        byte[] commandString = packetDataCodec.encodeString(this.command);
        byte[] timestampLong = packetDataCodec.encodeLong(this.timestamp);
        byte[] saltLong = packetDataCodec.encodeLong(this.salt);

        return byteArrayHelper.merge(packetIdVarInt, commandLengthVarInt, commandString, timestampLong, saltLong, remainingBytes);
    }
}
