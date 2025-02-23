package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PingRequestPacket;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PingRequestPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        long timestamp = packetDataCodec.readLong(packetData);
        return new PingRequestPacket(packetId, timestamp);
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.STATUS;
    }

    @Override
    public PacketDirection getParsedPacketDirection() {
        return PacketDirection.CLIENT_TO_SERVER;
    }

    @Override
    public int getParsedPacketId() {
        return 1;
    }
}
