package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;

public interface PacketHandler {
    Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType);
    Class<? extends Packet> getHandledPacketClass();
}
