package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.ConnectedProxyConnections;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameProfilePacketHandler implements PacketHandler {
    private final ConnectedProxyConnections connectedProxyConnections;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        if (clientType == ClientType.MAIN) {
            GameProfilePacket gameProfilePacket = (GameProfilePacket) packet;

            String username = gameProfilePacket.getUsername();
            UUID uuid = gameProfilePacket.getUuid();

            if (!connectedProxyConnections.containsProxyConnection(username)) {
                connectedProxyConnections.addProxyClient(username, proxyConnection);
            }

            proxyConnection.getMainProxyClient().setUserName(username);
            proxyConnection.getMainProxyClient().setUuid(uuid);
            proxyConnection.getMainProxyClient().setPacketState(PacketState.PLAY);
            return gameProfilePacket;
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return GameProfilePacket.class;
    }
}
