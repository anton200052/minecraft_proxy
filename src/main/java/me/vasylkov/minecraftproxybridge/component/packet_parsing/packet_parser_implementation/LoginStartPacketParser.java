package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginStartPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoginStartPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        int usernameLength = packetDataCodec.readVarInt(packetData);
        String username = packetDataCodec.readString(packetData, usernameLength);
        boolean hasSigData = packetDataCodec.readBoolean(packetData);
        boolean hasUUID = packetDataCodec.readBoolean(packetData);
        UUID uuid = packetDataCodec.readUUID(packetData);
        return new LoginStartPacket(packetId, uuid, username);
    }

    @Override
    public PacketState getParsedPacketState() {
        return PacketState.LOGIN;
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
