package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SystemChatMessagePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;

import java.util.Arrays;
import java.util.List;

public abstract class HelpCommandHandler implements CommandHandler {
    protected final ExtraPacketSender extraPacketSender;

    protected HelpCommandHandler(ExtraPacketSender extraPacketSender) {
        this.extraPacketSender = extraPacketSender;
    }

    @Override
    public void handleCommand(ProxyConnection proxyConnection, String command, String[] args) {
        int compressionThreshold = proxyConnection.getServerData().getCompressionThreshold();

        // Верхняя рамка
        extraPacketSender.sendExtraPacketToMainClient(proxyConnection, getSpecificChatMessagePacket(createChatMessage(Arrays.asList(new ChatMessage.Extra(true, false, false, false, false, "dark_gray", "╔══════ "), new ChatMessage.Extra(true, false, false, false, false, "aqua", "[ CHEAT HELP ]"), new ChatMessage.Extra(true, false, false, false, false, "dark_gray", " ══════╗")))), compressionThreshold);

        // Строка с .help
        extraPacketSender.sendExtraPacketToMainClient(proxyConnection, getSpecificChatMessagePacket(createChatMessage(Arrays.asList(new ChatMessage.Extra(false, false, false, false, false, "gray", "  .help"), new ChatMessage.Extra(false, false, false, false, false, "white", " - Помощь по командам чита")))), compressionThreshold);

        // Нижняя рамка
        extraPacketSender.sendExtraPacketToMainClient(proxyConnection, getSpecificChatMessagePacket(createChatMessage(List.of(new ChatMessage.Extra(true, false, false, false, false, "dark_gray", "╚════════════════════╝")))), compressionThreshold);
    }

    private ChatMessage createChatMessage(List<ChatMessage.Extra> extras) {
        return new ChatMessage(extras, "");
    }

    protected abstract SystemChatMessagePacket getSpecificChatMessagePacket(ChatMessage chatMessage);
}
