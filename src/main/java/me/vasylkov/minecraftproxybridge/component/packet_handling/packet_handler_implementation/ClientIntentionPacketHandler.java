package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientIntentionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.MainProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientIntentionPacketHandler implements PacketHandler{
    private final ProxyConfiguration proxyConfiguration;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        ClientIntentionPacket clientIntentionPacket = (ClientIntentionPacket) packet;
        clientIntentionPacket.setServerAddress(proxyConfiguration.getProxyInfo().getTargetServerAddress());

        int nextState = clientIntentionPacket.getNextState();
        PacketState newState = (nextState == 1) ? PacketState.STATUS : PacketState.LOGIN;

        MainProxyClient mainProxyClient = proxyConnection.getMainProxyClient();
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();

        ServerVersion serverVersion = ServerVersion.fromProtocolVersion(clientIntentionPacket.getProtocolVersion());

        if (clientType == ClientType.MAIN) {
            mainProxyClient.setPacketState(newState);
            mainProxyClient.setServerVersion(serverVersion);
        } else {
            mirrorProxyClient.setPacketState(newState);
            mirrorProxyClient.setServerVersion(serverVersion);
        }

        return clientIntentionPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientIntentionPacket.class;
    }
}
