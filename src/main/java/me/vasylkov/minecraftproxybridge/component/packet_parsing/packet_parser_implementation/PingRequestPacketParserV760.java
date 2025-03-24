package me.vasylkov.minecraftproxybridge.component.packet_parsing.packet_parser_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketParserKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PingRequestPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingRequestPacketParserV760 extends PingRequestPacketParser {

    public PingRequestPacketParserV760(PacketDataCodec packetDataCodec) {
        super(packetDataCodec);
    }

    @Override
    protected Packet parseSpecific(int packetId, PacketDirection packetDirection, long timestamp) {
        return new PingRequestPacketV760(packetId, timestamp);
    }

    @Override
    public List<PacketParserKey> getSupportedKeys() {
        return List.of(
                new PacketParserKey(
                        ServerVersion.V1_19_2, // V760
                        1,
                        PacketState.STATUS,
                        PacketDirection.CLIENT_TO_SERVER
                )
                      );
    }
}

