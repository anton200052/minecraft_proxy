package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketDirection;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class PacketHelper {
    private final Logger logger;

    public byte[] readRemainingPacketData(InputStream input, int packetLength) throws IOException {
        byte[] buffer = new byte[packetLength];
        int totalBytesRead = 0;
        while (totalBytesRead < packetLength) {
            int bytesRead = input.read(buffer, totalBytesRead, packetLength - totalBytesRead);

            if (bytesRead == -1) {
                break;
            }

            totalBytesRead += bytesRead;
        }
        return buffer;
    }

    /*public void printPacketData(byte[] buffer, PacketDirection packetDirection, PacketState packetState, ClientType clientType) {
        StringBuilder hexData = new StringBuilder();
        for (byte b : buffer) {
            hexData.append(String.format("%02X ", b));
        }

        if (clientType == ClientType.MIRROR) {
            logger.info("{}: {} {} {}", packetDirection, clientType, packetState, hexData.toString().trim());
        }
    }*/
}
