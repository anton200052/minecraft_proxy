package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;

import java.io.IOException;
import java.io.InputStream;

public abstract class ClientChatPacketParser implements PacketParser {
    protected final PacketDataCodec packetDataCodec;

    protected ClientChatPacketParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection direction, InputStream data) throws IOException {
        int messageLength = packetDataCodec.readVarInt(data);
        String message = packetDataCodec.readString(data, messageLength);

        return parseSpecific(packetId, direction, data, message);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String message) throws IOException;
}
