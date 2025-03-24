package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;

import java.io.IOException;
import java.io.InputStream;

public abstract class LoginStartPacketParser implements PacketParser {
    protected final PacketDataCodec packetDataCodec;

    protected LoginStartPacketParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        int usernameLength = packetDataCodec.readVarInt(packetData);
        String username = packetDataCodec.readString(packetData, usernameLength);

        return parseSpecific(packetId, packetDirection, packetData, username);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String username) throws IOException;

}
