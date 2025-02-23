package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.LoginPlayPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class LoginPlayPacketHandler implements PacketHandler {
    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();

        if (clientType == ClientType.MAIN
                && mirrorProxyClient != null
                && mirrorProxyClient.getSocket() != null
                && !mirrorProxyClient.getSocket().isClosed()
                && !mirrorProxyClient.isForwardingToMirrorProxyAllowed()
                && mirrorProxyClient.getPacketState() == PacketState.PLAY) {
           mirrorProxyClient.setForwardingToMirrorProxyAllowed(true);
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return LoginPlayPacket.class;
    }
}
