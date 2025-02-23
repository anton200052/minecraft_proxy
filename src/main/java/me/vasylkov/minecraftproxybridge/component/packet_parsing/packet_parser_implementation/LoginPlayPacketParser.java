package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginPlayPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class LoginPlayPacketParser implements PacketParser {
    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        return new LoginPlayPacket(packetId, packetData.readAllBytes());
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.PLAY;
    }

    @Override
    public PacketDirection getParsedPacketDirection() {
        return PacketDirection.SERVER_TO_CLIENT;
    }

    @Override
    public int getParsedPacketId() {
        return 37;
    }
}
