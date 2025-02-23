package me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation.PacketHandler;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PacketHandlingDispatcher {
    private final Map<Class<? extends Packet>, PacketHandler> handlers = new HashMap<>();

    public PacketHandlingDispatcher(List<PacketHandler> handlerList) {
        handlerList.forEach(handler -> handlers.put(handler.getHandledPacketClass(), handler));
    }

    public Packet handlePacket(ProxyClient proxyClient, ClientType clientType, Packet packet) {
        PacketHandler handler = handlers.get(packet.getClass());
        if (handler != null) {
            return handler.handlePacket(proxyClient, packet, clientType);
        }
        return packet;
    }
}

