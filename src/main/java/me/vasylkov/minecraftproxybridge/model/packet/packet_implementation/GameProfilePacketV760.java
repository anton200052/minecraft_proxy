package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class GameProfilePacketV760 extends GameProfilePacket {
    private byte[] remainingBytes;

    public GameProfilePacketV760(int packetId, UUID uuid, String username, byte[] remainingBytes) {
        super(packetId, uuid, username);
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] baseClassBytes = super.toRawData(packetDataCodec, byteArrayHelper);
        return byteArrayHelper.merge(baseClassBytes, remainingBytes);
    }
}
