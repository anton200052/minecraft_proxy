package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;

import java.io.IOException;
import java.io.InputStream;

public abstract class ChatCommandParser implements PacketParser {
    protected final PacketDataCodec packetDataCodec;

    protected ChatCommandParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection direction, InputStream data) throws IOException {
        int commandLength = packetDataCodec.readVarInt(data);
        String command = packetDataCodec.readString(data, commandLength);
        long timestamp = packetDataCodec.readLong(data);
        long salt = packetDataCodec.readLong(data);
        byte[] remainingBytes = data.readAllBytes();

        return parseSpecific(packetId, direction, data, command, timestamp, salt, remainingBytes);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String command, long timestamp, long salt, byte[] remainingBytes) throws IOException;
}
