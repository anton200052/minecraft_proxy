package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;

import java.io.IOException;

@Getter
@Setter
public class SynchronizePlayerPosPacketV760 extends SynchronizePlayerPosPacket {
    private boolean dismountVehicle;

    public SynchronizePlayerPosPacketV760(int packetId, double x, double y, double z, float yaw, float pitch, byte relativeFlags, int teleportId, boolean dismountVehicle) {
        super(packetId, x, y, z, yaw, pitch, relativeFlags, teleportId);
        this.dismountVehicle = dismountVehicle;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] baseClassBytes = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] dismountVehicleBoolean = packetDataCodec.encodeBoolean(dismountVehicle);

        return byteArrayHelper.merge(baseClassBytes, dismountVehicleBoolean);
    }
}
