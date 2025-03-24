package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlerKey;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SystemChatMessagePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SystemChatMessagePacketV754;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelpCommandHandlerV754 extends HelpCommandHandler {
    public HelpCommandHandlerV754(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    protected SystemChatMessagePacket getSpecificChatMessagePacket(ChatMessage chatMessage) {
        return new SystemChatMessagePacketV754(14, chatMessage, (byte) 1, new UUID(0, 0));
    }

    @Override
    public CommandHandlerKey getHandledCommandKey() {
        return new CommandHandlerKey(".help",
                                     ServerVersion.V1_16_5);
    }
}
