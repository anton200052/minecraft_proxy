package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Packet {
    private int packetId;

    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        return packetDataCodec.encodeVarInt(getPacketId());
    }
}
