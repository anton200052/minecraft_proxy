package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class LoginStartPacketV760 extends LoginStartPacket {
    private java.util.UUID uuid;

    public LoginStartPacketV760(int packetId, UUID uuid, String username) {
        super(packetId, username);
        this.uuid = uuid;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] basePacketBytes = super.toRawData(packetDataCodec, byteArrayHelper);

        byte[] hasSigDataBoolean = packetDataCodec.encodeBoolean(false);
        byte[] hasUUIDBoolean = packetDataCodec.encodeBoolean(true);
        byte[] packetUUID = packetDataCodec.encodeUUID(uuid);

        return byteArrayHelper.merge(basePacketBytes, hasSigDataBoolean, hasUUIDBoolean, packetUUID);
    }
}
