package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools.ChatCommandParser;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ChatCommandPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatCommand;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatCommandPacketHandler implements PacketHandler {
    private final ChatCommandParser chatCommandParser;
    private final CommandHandlingDispatcher commandHandlingDispatcher;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        ChatCommandPacket chatCommandPacket = (ChatCommandPacket) packet;
        ChatCommand chatCommand = chatCommandParser.parseChatCommand("/" + chatCommandPacket.getCommand());
        commandHandlingDispatcher.handleCommand(proxyConnection, chatCommand.getCommand(), chatCommand.getArguments());
        return chatCommandPacket;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ChatCommandPacket.class;
    }
}
