package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public abstract class GameProfilePacket extends Packet {
    private UUID uuid;
    private String username;

    protected GameProfilePacket(int packetId, UUID uuid, String username) {
        super(packetId);
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] packetUUID = packetDataCodec.encodeUUID(uuid);
        byte[] usernameLengthVarInt = packetDataCodec.encodeVarInt(username.length());
        byte[] usernameString = packetDataCodec.encodeString(username);

        return byteArrayHelper.merge(packetIdVarInt, packetUUID, usernameLengthVarInt, usernameString);
    }
}
