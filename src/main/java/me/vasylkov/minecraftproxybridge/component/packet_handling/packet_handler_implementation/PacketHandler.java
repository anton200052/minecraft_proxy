package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;


public interface PacketHandler {
    Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType);
    Class<? extends Packet> getHandledPacketClass();
}
