package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovePlayerPosPacketParserV760 extends MovePlayerPosPacketParser {

    public MovePlayerPosPacketParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection packetDirection,
                                   double x, double y, double z, boolean onGround) {
        return new MovePlayerPosPacketV760(packetId, x, y, z, onGround);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_19_2, // V760
                        20,
                        PacketState.PLAY,
                        PacketDirection.CLIENT_TO_SERVER
                )
                      );
    }
}