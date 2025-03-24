package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;

import java.io.IOException;
import java.io.InputStream;

public abstract class StatusRequestPacketParser implements PacketParser {

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        byte[] remaining = packetData.readAllBytes();
        return parseSpecific(packetId, packetDirection, remaining);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection packetDirection, byte[] remainingBytes) throws IOException;
}