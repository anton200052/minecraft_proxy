package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
public class GameProfilePacketParserV760 extends GameProfilePacketParser {
    public GameProfilePacketParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, UUID uuid, String username) throws IOException {
        byte[] remainingBytes = data.readAllBytes();
        return new GameProfilePacketV760(packetId, uuid, username, remainingBytes);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_19_2,
                        2,
                        PacketState.LOGIN,
                        PacketDirection.SERVER_TO_CLIENT
                )
                      );
    }
}
