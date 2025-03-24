package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public abstract class PongResponsePacket extends Packet {
    private long timestamp;

    protected PongResponsePacket(int packetId, long timestamp) {
        super(packetId);
        this.timestamp = timestamp;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] timestampLong = packetDataCodec.encodeLong(timestamp);

        return byteArrayHelper.merge(packetIdVarInt, timestampLong);
    }
}
