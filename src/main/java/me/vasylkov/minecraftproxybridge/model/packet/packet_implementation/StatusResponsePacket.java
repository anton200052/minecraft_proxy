package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public abstract class StatusResponsePacket extends Packet {
    private MinecraftServerInfo minecraftServerInfo;

    protected StatusResponsePacket(int packetId, MinecraftServerInfo minecraftServerInfo) {
        super(packetId);
        this.minecraftServerInfo = minecraftServerInfo;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String serverInfo = objectMapper.writeValueAsString(minecraftServerInfo);

        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] serverInfoLengthVarInt = packetDataCodec.encodeVarInt(serverInfo.length());
        byte[] serverInfoString = packetDataCodec.encodeString(serverInfo);

        return byteArrayHelper.merge(packetIdVarInt, serverInfoLengthVarInt, serverInfoString);
    }

    @Data
    @AllArgsConstructor
    public static class MinecraftServerInfo {
        private boolean previewsChat;
        private boolean enforcesSecureChat;
        private Description description;
        private Players players;
        private Version version;

        @Data
        @AllArgsConstructor
        public static class Description {
            private String text;
        }

        @Data
        @AllArgsConstructor
        public static class Players {
            private int max;
            private int online;
        }

        @Data
        @AllArgsConstructor
        public static class Version {
            private String name;
            private int protocol;
        }
    }

}
