package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameProfilePacketHandler implements PacketHandler {
    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        GameProfilePacket gameProfilePacket = (GameProfilePacket) packet;
        proxyConnection.getMainProxyClient().setUserName(gameProfilePacket.getUsername());
        proxyConnection.getMainProxyClient().setUuid(gameProfilePacket.getUuid());
        proxyConnection.getMainProxyClient().setPacketState(PacketState.PLAY);
        return gameProfilePacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return GameProfilePacket.class;
    }
}
