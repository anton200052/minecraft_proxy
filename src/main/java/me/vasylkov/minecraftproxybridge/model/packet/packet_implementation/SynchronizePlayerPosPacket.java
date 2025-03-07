package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class SynchronizePlayerPosPacket extends Packet {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private byte relativeFlags;
    private int teleportId;
    private boolean dismountVehicle;

    public SynchronizePlayerPosPacket(int packetId, double x, double y, double z, float yaw, float pitch, byte relativeFlags, int teleportId, boolean dismountVehicle) {
        super(packetId);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.relativeFlags = relativeFlags;
        this.teleportId = teleportId;
        this.dismountVehicle = dismountVehicle;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] packetIdVarInt = packetDataCodec.encodeVarInt(getPacketId());
        byte[] xDouble = packetDataCodec.encodeDouble(x);
        byte[] yDouble = packetDataCodec.encodeDouble(y);
        byte[] zDouble = packetDataCodec.encodeDouble(z);
        byte[] yawFloat = packetDataCodec.encodeFloat(yaw);
        byte[] pitchFloat  = packetDataCodec.encodeFloat(pitch);
        byte[] relativeFlagsByte = packetDataCodec.encodeByte(relativeFlags);
        byte[] teleportIdVarInt = packetDataCodec.encodeVarInt(teleportId);
        byte[] dismountVehicleBoolean = packetDataCodec.encodeBoolean(dismountVehicle);

        return byteArrayHelper.merge(packetIdVarInt, xDouble, yDouble, zDouble, yawFloat, pitchFloat, relativeFlagsByte, teleportIdVarInt, dismountVehicleBoolean);
    }
}
