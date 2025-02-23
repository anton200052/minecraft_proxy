package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketEncoder;
import me.vasylkov.minecraftproxybridge.exception.ExtraPacketSendingException;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ExtraPacketSender {
    private final PacketEncoder encoder;
    private final PacketRawDataSender packetRawDataSender;
    private final Logger logger;

    public void sendExtraPacketToMainClient(ProxyClient.ClientConnection connection, Packet packet, int compressionThreshold) {
        try {
            byte[] packetData = encoder.encodePacket(packet, compressionThreshold);
            packetRawDataSender.sendPacketDataToMainClient(connection, packetData);
        } catch (IOException e) {
            throw new ExtraPacketSendingException("Ошибка отправки доп. пакета основному клиенту", e);
        }
    }

    public void sendExtraPacketToMirrorClient(ProxyClient.ClientConnection connection, Packet packet, int compressionThreshold) {
        try {
            byte[] packetData = encoder.encodePacket(packet, compressionThreshold);
            packetRawDataSender.sendPacketDataToMirrorClient(connection, packetData);
        } catch (IOException e) {
            throw new ExtraPacketSendingException("Ошибка отправки доп. пакета зеракальному клиенту", e);
        }
    }

    public void sendExtraPacketDataToServer(ProxyClient.ClientConnection connection, Packet packet, int compressionThreshold) {
        try {
            byte[] packetData = encoder.encodePacket(packet, compressionThreshold);
            packetRawDataSender.sendPacketDataToServer(connection, packetData);
        } catch (IOException e) {
            throw new ExtraPacketSendingException("Ошибка отправки доп. пакета серверу", e);
        }
    }
}
