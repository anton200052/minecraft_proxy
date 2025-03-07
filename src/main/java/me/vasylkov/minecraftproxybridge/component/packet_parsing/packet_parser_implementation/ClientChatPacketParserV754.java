package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class ClientChatPacketParserV754 extends ClientChatPacketParser {
    public ClientChatPacketParserV754(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String message) {
        return new ClientChatPacketV754(packetId, message);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(new PacketParserKey(ServerVersion.V1_16_5, 3, PacketState.PLAY, PacketDirection.CLIENT_TO_SERVER));
    }
}
