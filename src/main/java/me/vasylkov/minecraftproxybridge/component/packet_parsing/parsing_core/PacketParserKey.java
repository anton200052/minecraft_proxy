package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;

public record PacketParserKey(ServerVersion serverVersion, int packetId, PacketState state, PacketDirection packetDirection) {}
