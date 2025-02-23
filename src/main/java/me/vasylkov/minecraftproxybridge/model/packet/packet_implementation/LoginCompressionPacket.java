package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class LoginCompressionPacket extends Packet {
    private int compression;

    public LoginCompressionPacket(int packetId, int compression) {
        super(packetId);
        this.compression = compression;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] compressionVarInt = packetDataCodec.encodeVarInt(this.compression);

        return byteArrayHelper.merge(packetIdVarInt, compressionVarInt);
    }

    @Override
    public String toString() {
        return "LoginCompressionPacket{" + "compression=" + compression + '}';
    }
}
