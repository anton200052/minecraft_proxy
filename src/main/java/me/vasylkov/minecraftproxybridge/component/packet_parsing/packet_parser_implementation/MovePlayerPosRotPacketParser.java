package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;

import java.io.IOException;
import java.io.InputStream;

public abstract class MovePlayerPosRotPacketParser implements PacketParser {

    protected final PacketDataCodec packetDataCodec;

    protected MovePlayerPosRotPacketParser(PacketDataCodec packetDataCodec) {
        this.packetDataCodec = packetDataCodec;
    }

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        double x = packetDataCodec.readDouble(packetData);
        double y = packetDataCodec.readDouble(packetData);
        double z = packetDataCodec.readDouble(packetData);
        float yaw = packetDataCodec.readFloat(packetData);
        float pitch = packetDataCodec.readFloat(packetData);
        boolean onGround = packetDataCodec.readBoolean(packetData);

        return parseSpecific(packetId, packetDirection, x, y, z, yaw, pitch, onGround);
    }

    protected abstract Packet parseSpecific(int packetId, PacketDirection packetDirection,
                                            double x, double y, double z,
                                            float yaw, float pitch, boolean onGround) throws IOException;
}

