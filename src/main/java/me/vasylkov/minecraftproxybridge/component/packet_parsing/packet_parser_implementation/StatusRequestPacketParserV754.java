package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusRequestPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusRequestPacketParserV754 extends StatusRequestPacketParser {

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection packetDirection, byte[] remainingBytes) {
        return new StatusRequestPacketV754(packetId, remainingBytes);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_16_5, // V754
                        0,
                        PacketState.STATUS,
                        PacketDirection.CLIENT_TO_SERVER
                )
                      );
    }
}

