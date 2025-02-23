package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class LoginPlayPacket extends Packet {
    private byte[] bytes; //todo: parse all packet fields include NBT

    public LoginPlayPacket(int packetId, byte[] bytes) {
        super(packetId);
        this.bytes = bytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        return byteArrayHelper.merge(packetIdVarInt, bytes);
    }
}
