package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ClientChatPacketParserV760 extends ClientChatPacketParser {

    public ClientChatPacketParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String message) throws IOException {
        long timestamp = packetDataCodec.readLong(data);
        long salt = packetDataCodec.readLong(data);
        byte[] remainingBytes = data.readAllBytes();
        return new ClientChatPacketV760(packetId, message, timestamp, salt, remainingBytes);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(new PacketParserKey(ServerVersion.V1_19_2, 5, PacketState.PLAY, PacketDirection.CLIENT_TO_SERVER));
    }
}
