package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientIntentionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
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

        if (clientType == ClientType.MAIN) {
            proxyConnection.getMainProxyClient().setPacketState(newState);
        } else {
            proxyConnection.getMirrorProxyClient().setPacketState(newState);
        }

        return clientIntentionPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientIntentionPacket.class;
    }
}
