package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlingDispatcher;
import me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools.ChatCommandParser;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.ClientChatPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatCommand;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientChatPacketHandler implements PacketHandler {
    private final ChatCommandParser chatCommandParser;
    private final CommandHandlingDispatcher commandHandlingDispatcher;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        ClientChatPacket chatPacket = (ClientChatPacket) packet;
        String message = chatPacket.getMessage();

        if (clientType == ClientType.MAIN) {
            if (message.startsWith(".")) {
                ChatCommand chatCommand = chatCommandParser.parseChatCommand(message);
                commandHandlingDispatcher.handleCommand(proxyConnection, chatCommand.getCommand(), chatCommand.getArguments());
                return null;
            }
            else if (message.startsWith("/")) {
                ChatCommand chatCommand = chatCommandParser.parseChatCommand(message);
                commandHandlingDispatcher.handleCommand(proxyConnection, chatCommand.getCommand(), chatCommand.getArguments());
            }
        }

        return chatPacket;
    }


    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return ClientChatPacket.class;
    }
}
