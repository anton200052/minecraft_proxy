package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class ClientChatPacketV760 extends ClientChatPacket {
    private long timestamp;
    private long salt;
    private byte[] remainingBytes;


    public ClientChatPacketV760(int packetId, String message, long timestamp, long salt, byte[] remainingBytes) {
        super(packetId, message);
        this.timestamp = timestamp;
        this.salt = salt;
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] baseClassBytes = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] timeStampLong = packetDataCodec.encodeLong(this.timestamp);
        byte[] saltLong = packetDataCodec.encodeLong(this.salt);

        return byteArrayHelper.merge(baseClassBytes, timeStampLong, saltLong, this.remainingBytes);
    }
}
