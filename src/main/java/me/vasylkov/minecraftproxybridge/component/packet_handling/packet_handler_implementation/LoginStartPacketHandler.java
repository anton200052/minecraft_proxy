package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginCompressionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginStartPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginStartPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        if (clientType == ClientType.MIRROR) {
            int mainCompression = proxyConnection.getMainProxyClient().getCompressionThreshold();

            MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
            mirrorProxyClient.setCompressionThreshold(mainCompression);

            LoginCompressionPacket loginCompressionPacket = new LoginCompressionPacket(3, mainCompression);
            GameProfilePacket gameProfilePacket = new GameProfilePacket(
                    2,
                    proxyConnection.getMainProxyClient().getUuid(),
                    proxyConnection.getMainProxyClient().getUserName(),
                    new byte[]{0}
            );

            extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, loginCompressionPacket, 0);
            extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, gameProfilePacket, mainCompression);

            mirrorProxyClient.setPacketState(PacketState.PLAY);
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginStartPacket.class;
    }
}
