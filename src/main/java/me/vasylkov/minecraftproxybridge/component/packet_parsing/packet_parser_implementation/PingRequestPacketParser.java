package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;

import java.io.IOException;
import java.io.InputStream;

public abstract class PingRequestPacketParser implements PacketParser {

    protected final PacketDataCodec packetDataCodec;

    protected PingRequestPacketParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        long timestamp = packetDataCodec.readLong(packetData);
        return parseSpecific(packetId, packetDirection, timestamp);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection packetDirection, long timestamp) throws IOException;
}