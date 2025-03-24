package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.model.proxy.ServerData;

public abstract class StatusRequestPacketHandler implements PacketHandler {
    protected final ExtraPacketSender extraPacketSender;

    protected StatusRequestPacketHandler(ExtraPacketSender extraPacketSender) {
        this.extraPacketSender = extraPacketSender;
    }

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        ServerData serverData = proxyConnection.getServerData();

        if (clientType == ClientType.MIRROR) {
            /*sendSpecificStatusResponsePacket(proxyConnection, serverData.getCompressionThreshold());*/
        }
        return packet;
    }
    public abstract void sendSpecificStatusResponsePacket(ProxyConnection proxyConnection, int compressionThreshold);
}