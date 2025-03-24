package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class GameProfilePacketParser implements PacketParser {
    protected final PacketDataCodec packetDataCodec;

    protected GameProfilePacketParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        UUID uuid = packetDataCodec.readUUID(packetData);
        int usernameLength = packetDataCodec.readVarInt(packetData);
        String username = packetDataCodec.readString(packetData, usernameLength);

        return parseSpecific(packetId, packetDirection, packetData, uuid, username);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, UUID uuid, String username) throws IOException;
}
