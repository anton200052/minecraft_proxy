package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PacketParser {
    Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException;
    List<PacketParserKey> getSupportedKeys();
}
