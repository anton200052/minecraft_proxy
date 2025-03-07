package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
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
    private final ConnectedProxyConnections connectedProxyConnections;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        LoginStartPacket loginStartPacket = (LoginStartPacket) packet;

        if (clientType == ClientType.MIRROR) {
            String username = loginStartPacket.getUsername();
            if (connectedProxyConnections.containsProxyConnection(username)) {
                MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();

                ProxyConnection mainProxyConnection = connectedProxyConnections.getProxyConnection(username);
                mainProxyConnection.setMirrorProxyClient(mirrorProxyClient);
                int mainCompression = mainProxyConnection.getMainProxyClient().getCompressionThreshold();
                mirrorProxyClient.setCompressionThreshold(mainCompression);

                proxyConnection.setMainProxyClient(mainProxyConnection.getMainProxyClient());
                proxyConnection.setServerData(mainProxyConnection.getServerData());

                LoginCompressionPacket loginCompressionPacket = new LoginCompressionPacket(3, mainCompression);
                GameProfilePacket gameProfilePacket = new GameProfilePacket(2, proxyConnection.getMainProxyClient().getUuid(), proxyConnection.getMainProxyClient().getUserName(), new byte[] {0});

                extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, loginCompressionPacket, 0);
                extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, gameProfilePacket, mainCompression);

                mirrorProxyClient.setPacketState(PacketState.PLAY);
            }
        }

        return loginStartPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginStartPacket.class;
    }
}
