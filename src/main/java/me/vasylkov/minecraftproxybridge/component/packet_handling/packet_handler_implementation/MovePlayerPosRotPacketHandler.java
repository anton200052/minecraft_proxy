package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovePlayerPosRotPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        ProxyClient.ClientConnection clientConnection = proxyClient.getConnection();

        if (clientType == ClientType.MAIN && proxyClient.getState().isForwardingToMirrorProxyAllowed() && clientConnection.getMirrorClientSocket() != null) {
            MovePlayerPosRotPacket movePlayerPosRotPacket = (MovePlayerPosRotPacket) packet;
            SynchronizePlayerPosPacket synchronizePlayerPosPacket = new SynchronizePlayerPosPacket(57, movePlayerPosRotPacket.getX(), movePlayerPosRotPacket.getY(), movePlayerPosRotPacket.getZ(), movePlayerPosRotPacket.getYaw(), movePlayerPosRotPacket.getPitch(), (byte) 0, 1, false);

            extraPacketSender.sendExtraPacketToMirrorClient(clientConnection, synchronizePlayerPosPacket, proxyClient.getData().getMainClientCompressionThreshold());
        }

        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return MovePlayerPosRotPacket.class;
    }
}
