package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public abstract class ClientChatPacket extends Packet {
    private String message;

    protected ClientChatPacket(int packetId, String message) {
        super(packetId);
        this.message = message;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] messageBytes = this.message.getBytes(StandardCharsets.UTF_8);
        byte[] messageLengthVarInt = packetDataCodec.encodeVarInt(messageBytes.length);
        byte[] messageString = packetDataCodec.encodeString(this.message);

        return byteArrayHelper.merge(packetIdVarInt, messageLengthVarInt, messageString);
    }
}
