package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientIntentionPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientIntentionPacketHandler implements PacketHandler{
    private final ProxyConfiguration proxyConfiguration;

    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        ClientIntentionPacket clientIntentionPacket = (ClientIntentionPacket) packet;
        clientIntentionPacket.setServerAddress(proxyConfiguration.getProxyInfo().getTargetServerAddress());

        ProxyClient.ClientState clientState = proxyClient.getState();

        int nextState = clientIntentionPacket.getNextState();
        PacketState packetState = nextState == 1 ? PacketState.STATUS : PacketState.LOGIN;
        if (clientType == ClientType.MAIN) {
            clientState.setMainProxyState(packetState);
        }
        else {
            clientState.setMirrorProxyState(packetState);
        }
        return clientIntentionPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientIntentionPacket.class;
    }
}
