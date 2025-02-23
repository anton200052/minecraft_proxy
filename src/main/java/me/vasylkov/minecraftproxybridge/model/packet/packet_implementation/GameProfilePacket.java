package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
public class GameProfilePacket extends Packet {
    private java.util.UUID uuid;
    private String username;
    private byte[] remainingBytes;

    public GameProfilePacket(int packetId, UUID uuid, String username, byte[] remainingBytes) {
        super(packetId);
        this.username = username;
        this.uuid = uuid;
        this.remainingBytes = remainingBytes;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] packetUUID = packetDataCodec.encodeUUID(uuid);
        byte[] usernameLengthVarInt = packetDataCodec.encodeVarInt(username.length());
        byte[] usernameString = packetDataCodec.encodeString(username);

        return byteArrayHelper.merge(packetIdVarInt, packetUUID, usernameLengthVarInt, usernameString, remainingBytes);
    }

    @Override
    public String toString() {
        return "GameProfilePacket{" + "uuid=" + uuid + ", username='" + username + '\'' + ", remainingBytes=" + Arrays.toString(remainingBytes) + '}';
    }
}
