package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovePlayerPosRotPacketParserV754 extends MovePlayerPosRotPacketParser {

    public MovePlayerPosRotPacketParserV754(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection packetDirection,
                                   double x, double y, double z,
                                   float yaw, float pitch, boolean onGround) {
        return new MovePlayerPosRotPacketV754(packetId, x, y, z, yaw, pitch, onGround);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_16_5, // V754
                        19,
                        PacketState.PLAY,
                        PacketDirection.CLIENT_TO_SERVER
                )
                      );
    }
}