package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
public class UnsignedPacket extends Packet {
    private final byte[] data;

    public UnsignedPacket(int packetId, byte[] data) {
        super(packetId);
        this.data = data;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());

        return byteArrayHelper.merge(packetIdVarInt, data);
    }

    @Override
    public String toString() {
        return "Packet Id : " + getPacketId();
    }
}
