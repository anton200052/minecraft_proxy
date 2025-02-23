package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.PacketRawDataSender;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketEncoder;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginCompressionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginStartPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginStartPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        if (clientType == ClientType.MIRROR) {
            ProxyClient.ClientData clientData = proxyClient.getData();
            int compressionThreshold = clientData.getMainClientCompressionThreshold();
            clientData.setMirrorClientCompressionThreshold(compressionThreshold);

            LoginCompressionPacket loginCompressionPacket = new LoginCompressionPacket(3, compressionThreshold);
            GameProfilePacket gameProfilePacket = new GameProfilePacket(2, clientData.getUuid(), clientData.getUserName(), new byte[] {0});

            extraPacketSender.sendExtraPacketToMirrorClient(proxyClient.getConnection(), loginCompressionPacket, 0);
            extraPacketSender.sendExtraPacketToMirrorClient(proxyClient.getConnection(), gameProfilePacket, compressionThreshold);

            proxyClient.getState().setMirrorProxyState(PacketState.PLAY);
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginStartPacket.class;
    }
}
