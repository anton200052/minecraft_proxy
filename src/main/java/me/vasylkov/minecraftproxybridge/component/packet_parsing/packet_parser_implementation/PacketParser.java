package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;

import java.io.IOException;
import java.io.InputStream;

public interface PacketParser {
    Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException;
    PacketState getParsedPacketState();
    PacketDirection getParsedPacketDirection();
    int getParsedPacketId();
}
