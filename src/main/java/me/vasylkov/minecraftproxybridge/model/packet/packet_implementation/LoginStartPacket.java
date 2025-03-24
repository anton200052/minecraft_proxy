package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public abstract class LoginStartPacket extends Packet {
    private String username;

    protected LoginStartPacket(int packetId, String username) {
        super(packetId);
        this.username = username;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] usernameLengthVarInt = packetDataCodec.encodeVarInt(username.length());
        byte[] usernameString = packetDataCodec.encodeString(username);

        return byteArrayHelper.merge(packetIdVarInt, usernameLengthVarInt, usernameString);
    }
}
