package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginPlayPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

@Component
public class LoginPlayPacketHandler implements PacketHandler {
    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        ProxyClient.ClientState clientState = proxyClient.getState();
        if (clientType == ClientType.MAIN && proxyClient.getConnection().getMirrorClientSocket() != null && !clientState.isForwardingToMirrorProxyAllowed() && clientState.getMirrorProxyState() == PacketState.PLAY) {
            proxyClient.getState().setForwardingToMirrorProxyAllowed(true);
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginPlayPacket.class;
    }
}
