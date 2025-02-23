package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientIntentionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ClientIntentionPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        int protocolVersion = packetDataCodec.readVarInt(packetData);
        int serverAddressLength = packetDataCodec.readVarInt(packetData);
        String serverAddress = packetDataCodec.readString(packetData, serverAddressLength);
        int serverPort = packetDataCodec.readUnsignedShort(packetData);
        int nextState = packetDataCodec.readVarInt(packetData);

        return new ClientIntentionPacket(packetId, protocolVersion, serverAddress, serverPort, nextState);
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.HANDSHAKE;
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
