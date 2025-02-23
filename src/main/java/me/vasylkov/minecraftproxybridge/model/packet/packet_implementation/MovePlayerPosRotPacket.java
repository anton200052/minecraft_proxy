package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class MovePlayerPosRotPacket extends Packet {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public MovePlayerPosRotPacket(int packetId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        super(packetId);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] xDouble = packetDataCodec.encodeDouble(x);
        byte[] yDouble = packetDataCodec.encodeDouble(y);
        byte[] zDouble = packetDataCodec.encodeDouble(z);
        byte[] yawFloat = packetDataCodec.encodeFloat(yaw);
        byte[] pitchFloat = packetDataCodec.encodeFloat(pitch);
        byte[] booleanOnGround = packetDataCodec.encodeBoolean(onGround);

        return byteArrayHelper.merge(packetIdVarInt, xDouble, yDouble, zDouble, yawFloat, pitchFloat, booleanOnGround);
    }
}
