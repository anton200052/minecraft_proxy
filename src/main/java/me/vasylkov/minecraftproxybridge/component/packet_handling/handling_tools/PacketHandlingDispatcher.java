package me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation.PacketHandler;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
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

    public Packet handlePacket(ProxyConnection proxyConnection, ClientType clientType, Packet packet) {
        PacketHandler handler = findHandler(packet.getClass());
        if (handler != null) {
            return handler.handlePacket(proxyConnection, packet, clientType);
        }
        return packet;
    }

    private PacketHandler findHandler(Class<?> packetClass) {
        if (packetClass == null || packetClass == Object.class) {
            return null;
        }
        PacketHandler handler = handlers.get(packetClass);
        if (handler != null) {
            return handler;
        }

        return findHandler(packetClass.getSuperclass());
    }
}

