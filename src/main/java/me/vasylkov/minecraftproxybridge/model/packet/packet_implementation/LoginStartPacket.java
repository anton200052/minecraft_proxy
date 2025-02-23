package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class LoginStartPacket extends Packet {
    private java.util.UUID uuid;
    private String username;

    public LoginStartPacket(int packetId, UUID uuid, String username) {
        super(packetId);
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] usernameLengthVarInt = packetDataCodec.encodeVarInt(username.length());
        byte[] usernameString = packetDataCodec.encodeString(username);
        byte[] hasSigDataBoolean = packetDataCodec.encodeBoolean(false);
        byte[] hasUUIDBoolean = packetDataCodec.encodeBoolean(true);
        byte[] packetUUID = packetDataCodec.encodeUUID(uuid);

        return byteArrayHelper.merge(packetIdVarInt, usernameLengthVarInt, usernameString, hasSigDataBoolean, hasUUIDBoolean, packetUUID);
    }
}
