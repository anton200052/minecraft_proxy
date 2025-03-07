package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation.PacketParser;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.UnsignedPacket;
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

    public PacketParserDispatcher(List<PacketParser> parserList, PacketDataCodec packetDataCodec) {
        for (PacketParser parser : parserList) {
            for (PacketParserKey key : parser.getSupportedKeys()) {
                parsers.put(key, parser);
            }
        }
        this.packetDataCodec = packetDataCodec;
    }

    public Packet parsePacket(ServerVersion serverVersion, PacketState packetState, PacketDirection packetDirection, byte[] dataBuffer) throws IOException {
        ByteArrayInputStream packetData = new ByteArrayInputStream(dataBuffer);
        int packetId = packetDataCodec.readVarInt(packetData);
        PacketParserKey key = new PacketParserKey(serverVersion, packetId, packetState, packetDirection);
        PacketParser parser = parsers.get(key);
        if (parser != null) {
            return parser.parsePacket(packetId, packetDirection, packetData);
        }
        return new UnsignedPacket(packetId, packetData.readAllBytes());
    }
}
