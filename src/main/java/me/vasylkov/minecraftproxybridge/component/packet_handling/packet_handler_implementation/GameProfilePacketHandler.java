package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.GameProfilePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameProfilePacketHandler implements PacketHandler {
    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        GameProfilePacket gameProfilePacket = (GameProfilePacket) packet;

        ProxyClient.ClientData clientData = proxyClient.getData();
        ProxyClient.ClientState clientState = proxyClient.getState();

        clientData.setUserName(gameProfilePacket.getUsername());
        clientData.setUuid(gameProfilePacket.getUuid());
        clientState.setMainProxyState(PacketState.PLAY);

        return gameProfilePacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return GameProfilePacket.class;
    }
}
