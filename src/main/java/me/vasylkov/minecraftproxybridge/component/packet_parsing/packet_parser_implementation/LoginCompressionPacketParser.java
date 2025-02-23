package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginCompressionPacket;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class LoginCompressionPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        int compression = packetDataCodec.readVarInt(packetData);
        return new LoginCompressionPacket(packetId, compression);
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.LOGIN;
    }

    @Override
    public PacketDirection getParsedPacketDirection() {
        return PacketDirection.SERVER_TO_CLIENT;
    }

    @Override
    public int getParsedPacketId() {
        return 3;
    }
}
