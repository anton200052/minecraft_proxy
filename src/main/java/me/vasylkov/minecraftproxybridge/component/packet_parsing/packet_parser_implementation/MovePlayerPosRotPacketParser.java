package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class MovePlayerPosRotPacketParser implements PacketParser {
    private final PacketDataCodec packetDataCodec;

    @Override
    public Packet parsePacket(int packetId, PacketDirection packetDirection, InputStream packetData) throws IOException {
        double x = packetDataCodec.readDouble(packetData);
        double y = packetDataCodec.readDouble(packetData);
        double z = packetDataCodec.readDouble(packetData);
        float yaw = packetDataCodec.readFloat(packetData);
        float pitch = packetDataCodec.readFloat(packetData);
        boolean onGround = packetDataCodec.readBoolean(packetData);

        return new MovePlayerPosRotPacket(packetId, x, y, z, yaw, pitch, onGround);
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
        return 21;
    }
}
