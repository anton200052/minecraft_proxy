package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ClientChatPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        int messageLength = packetDataCodec.readVarInt(packetData);
        String message = packetDataCodec.readString(packetData, messageLength);
        long timestamp = packetDataCodec.readLong(packetData);
        long salt = packetDataCodec.readLong(packetData);
        byte[] remainingBytes = packetData.readAllBytes();

        return new ClientChatPacket(packetId, message, timestamp, salt, remainingBytes);
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.PLAY;
    }

    @Override
    public PacketDirection getParsedPacketDirection() {
        return PacketDirection.CLIENT_TO_SERVER;
    }

    @Override
    public int getParsedPacketId() {
        return 5;
    }
}
