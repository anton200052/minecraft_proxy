package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class ClientIntentionPacket extends Packet {
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    public ClientIntentionPacket(int packetId, int protocolVersion, String serverAddress, int serverPort, int nextState) {
        super(packetId);
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] protocolVersionVarInt = packetDataCodec.encodeVarInt(this.protocolVersion);
        byte[] serverAddressLengthVarInt = packetDataCodec.encodeVarInt(this.serverAddress.length());
        byte[] serverAddressString = packetDataCodec.encodeString(this.serverAddress);
        byte[] serverPortUnsignedShort = packetDataCodec.encodeUnsignedShort(this.serverPort);
        byte[] nextStateVarInt = packetDataCodec.encodeVarInt(this.nextState);

        return byteArrayHelper.merge(packetIdVarInt, protocolVersionVarInt, serverAddressLengthVarInt, serverAddressString, serverPortUnsignedShort, nextStateVarInt);
    }
}
