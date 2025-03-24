package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.PingRequestPacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.model.proxy.ServerData;

public abstract class PingRequestPacketHandler implements PacketHandler {
    protected final ExtraPacketSender extraPacketSender;

    protected PingRequestPacketHandler(ExtraPacketSender extraPacketSender) {
        this.extraPacketSender = extraPacketSender;
    }

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        PingRequestPacket pingRequestPacket = (PingRequestPacket) packet;
        ServerData serverData = proxyConnection.getServerData();

        if (clientType == ClientType.MIRROR) {
            /*sendSpecificPongPacket(proxyConnection, serverData.getCompressionThreshold(), pingRequestPacket.getTimestamp());*/
        }

        return packet;
    }

    public abstract void sendSpecificPongPacket(ProxyConnection proxyConnection, int compressionThreshold, long timestamp);
}
