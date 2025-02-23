package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SynchronizePlayerPosPacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovePlayerPosRotPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();

        if (clientType == ClientType.MAIN
                && mirrorProxyClient != null
                && mirrorProxyClient.isForwardingToMirrorProxyAllowed()
                && mirrorProxyClient.getSocket() != null) {
            MovePlayerPosRotPacket movePacket = (MovePlayerPosRotPacket) packet;
            SynchronizePlayerPosPacket syncPacket = new SynchronizePlayerPosPacket(
                    57,
                    movePacket.getX(),
                    movePacket.getY(),
                    movePacket.getZ(),
                    movePacket.getYaw(),
                    movePacket.getPitch(),
                    (byte) 0,
                    1,
                    false
            );

            extraPacketSender.sendExtraPacketToMirrorClient(
                    proxyConnection,
                    syncPacket,
                    mirrorProxyClient.getCompressionThreshold()
                                                           );
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return MovePlayerPosRotPacket.class;
    }
}
