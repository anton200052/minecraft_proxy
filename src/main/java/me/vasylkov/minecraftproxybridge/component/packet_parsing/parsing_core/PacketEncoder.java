package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PacketEncoder {
    private final PacketDataCodec packetDataCodec;
    private final ByteArrayHelper byteArrayHelper;
    private final ZlibCompressor zlibCompressor;

    public byte[] encodePacket(Packet packet, int threshold) throws IOException {
        byte[] rawData = packet.toRawData(packetDataCodec, byteArrayHelper);
        if (threshold > 0) {
            return encodePacketWithCompression(rawData, threshold);
        }
        else {
            return encodePacketWithoutCompression(rawData);
        }
    }

    public byte[] encodePacketWithCompression(byte[] rawData, int threshold) throws IOException {
        int uncompressedLength = 0;
        byte[] compressedOrRaw;

        if (rawData.length >= threshold) {
            compressedOrRaw = zlibCompressor.compressZlib(rawData);
            uncompressedLength = rawData.length;
        } else {
            compressedOrRaw = rawData;
        }

        byte[] varIntUncompressedLength = packetDataCodec.encodeVarInt(uncompressedLength);

        int totalFrameLength = compressedOrRaw.length + varIntUncompressedLength.length;
        byte[] varIntPacketLength = packetDataCodec.encodeVarInt(totalFrameLength);

        return byteArrayHelper.merge(varIntPacketLength, varIntUncompressedLength, compressedOrRaw);
    }


    public byte[] encodePacketWithoutCompression(byte[] rawData) throws IOException {
        byte[] varIntPacketLength = packetDataCodec.encodeVarInt(rawData.length);
        return byteArrayHelper.merge(varIntPacketLength, rawData);
    }
}
