package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginStartPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
public class LoginStartPacketParserV760 extends LoginStartPacketParser {
    protected LoginStartPacketParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection direction, InputStream data, String username) throws IOException {
        boolean hasSigData = packetDataCodec.readBoolean(data);
        boolean hasUUID = packetDataCodec.readBoolean(data);
        UUID uuid = packetDataCodec.readUUID(data);
        return new LoginStartPacketV760(packetId, uuid, username);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_19_2,
                        0,
                        PacketState.LOGIN,
                        PacketDirection.CLIENT_TO_SERVER
                )
                      );
    }
}
