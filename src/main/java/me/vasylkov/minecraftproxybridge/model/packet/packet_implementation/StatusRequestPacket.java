package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class StatusRequestPacket extends Packet {
    private byte[] remainingBytes;

    public StatusRequestPacket(int packetId, byte[] remainingBytes) {
        super(packetId);
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());

        return byteArrayHelper.merge(packetIdVarInt, remainingBytes);
    }
}
