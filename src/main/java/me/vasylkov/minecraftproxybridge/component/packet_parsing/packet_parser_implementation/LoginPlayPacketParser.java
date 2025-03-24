package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;

import java.io.IOException;
import java.io.InputStream;

public abstract class LoginPlayPacketParser implements PacketParser {
    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        byte[] allBytes = packetData.readAllBytes();
        return parseSpecific(packetId, packetDirection, allBytes);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection packetDirection, byte[] data) throws IOException;
}

