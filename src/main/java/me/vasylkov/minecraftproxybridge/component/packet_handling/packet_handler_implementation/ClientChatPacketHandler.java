package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ClientChatPacketHandler implements PacketHandler {
    private final CommandHandlingDispatcher commandHandlingDispatcher;

    @Override
    public Packet handlePacket(ProxyClient proxyClient, Packet packet, ClientType clientType) {
        ClientChatPacket chatPacket = (ClientChatPacket) packet;
        String message = chatPacket.getMessage();

        if (clientType == ClientType.MAIN && message.startsWith(".")) {
            String[] parts = message.trim().split("\\s+");
            String command = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            commandHandlingDispatcher.handleCommand(proxyClient, command, args);
            return null;
        }

        return chatPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientChatPacket.class;
    }
}
