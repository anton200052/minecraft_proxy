package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ChatCommandPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ChatCommandParserV760 extends ChatCommandParser {
    public ChatCommandParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String command, long timestamp, long salt, byte[] remainingBytes) throws IOException {
        return new ChatCommandPacketV760(packetId, command, timestamp, salt, remainingBytes);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(new PacketParserKey(ServerVersion.V1_19_2, 4, PacketState.PLAY, PacketDirection.CLIENT_TO_SERVER));
    }
}
