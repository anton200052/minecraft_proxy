package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusRequestPacket;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class StatusRequestPacketParser implements PacketParser {
    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        return new StatusRequestPacket(packetId, packetData.readAllBytes());
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
        return 0;
    }
}
