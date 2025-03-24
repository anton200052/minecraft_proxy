package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginPlayPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginPlayPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginPlayPacketParserV760 extends LoginPlayPacketParser {

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection packetDirection, byte[] data) {
        return new LoginPlayPacketV760(packetId, data);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_19_2, // V760
                        37, // исходный ID для V1_19_2
                        PacketState.PLAY,
                        PacketDirection.SERVER_TO_CLIENT
                )
                      );
    }
}
