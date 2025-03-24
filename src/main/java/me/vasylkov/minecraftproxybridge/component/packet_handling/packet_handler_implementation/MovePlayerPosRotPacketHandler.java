package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.MovePlayerPosRotPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.model.proxy.ServerData;

public abstract class MovePlayerPosRotPacketHandler implements PacketHandler {
    protected final ExtraPacketSender extraPacketSender;

    protected MovePlayerPosRotPacketHandler(ExtraPacketSender extraPacketSender) {
        this.extraPacketSender = extraPacketSender;
    }

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        MirrorProxyClient mirrorProxyClient = proxyConnection.getMirrorProxyClient();
        ServerData serverData = proxyConnection.getServerData();

        if (clientType == ClientType.MAIN
                && mirrorProxyClient != null
                && mirrorProxyClient.isForwardingToMirrorProxyAllowed()
                && mirrorProxyClient.getSocket() != null) {
            MovePlayerPosRotPacket movePacket = (MovePlayerPosRotPacket) packet;

            sendSpecificSyncPacket(proxyConnection, serverData.getCompressionThreshold(), movePacket.getX(), movePacket.getY(), movePacket.getZ(), movePacket.getYaw(), movePacket.getPitch());
        }
        return packet;
    }

    protected abstract void sendSpecificSyncPacket(ProxyConnection proxyConnection, int compressionThreshold, double x, double y, double z, float yaw, float pitch);
}
