package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation.PacketParser;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.UnsignedPacket;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PacketParserDispatcher {
    private final PacketDataCodec packetDataCodec;
    private final Map<PacketParserKey, PacketParser> parsers = new HashMap<>();

    public PacketParserDispatcher(List<PacketParser> parserList, Logger logger, PacketDataCodec packetDataCodec) {
        parserList.forEach(parser -> {
            PacketParserKey key = new PacketParserKey(parser.getParsedPacketState(), parser.getParsedPacketId(), parser.getParsedPacketDirection());
            parsers.put(key, parser);
        });
        this.packetDataCodec = packetDataCodec;
    }

    public Packet parsePacket(PacketState packetState, PacketDirection packetDirection, byte[] dataBuffer) throws IOException {
        ByteArrayInputStream packetData = new ByteArrayInputStream(dataBuffer);
        int packetId = packetDataCodec.readVarInt(packetData);
        PacketParserKey key = new PacketParserKey(packetState, packetId, packetDirection);
        PacketParser parser = parsers.get(key);
        if (parser != null) {
            return parser.parsePacket(packetId, packetDirection, packetData);
        }
        return new UnsignedPacket(packetId, packetData.readAllBytes());
    }
}
