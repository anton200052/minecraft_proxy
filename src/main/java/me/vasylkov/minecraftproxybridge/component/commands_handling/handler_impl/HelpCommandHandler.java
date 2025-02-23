package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.SystemChatLineSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpCommandHandler implements CommandHandler {
    private final SystemChatLineSender chatLineSender;

    @Override
    public void handleCommand(ProxyClient proxyClient, String command, String[] args) {
        ProxyClient.ClientConnection clientConnection = proxyClient.getConnection();
        int compressionThreshold = proxyClient.getData().getMainClientCompressionThreshold();

        // Верхняя рамка
        chatLineSender.sendChatLine(clientConnection, compressionThreshold, Arrays.asList(new ChatMessage.Extra(true, false, false, false, false, "dark_gray", "╔══════ "), new ChatMessage.Extra(true, false, false, false, false, "aqua", "[ CHEAT HELP ]"), new ChatMessage.Extra(true, false, false, false, false, "dark_gray", " ══════╗")));

        // Строка с .help
        chatLineSender.sendChatLine(clientConnection, compressionThreshold, Arrays.asList(new ChatMessage.Extra(false, false, false, false, false, "gray", "  .help"), new ChatMessage.Extra(false, false, false, false, false, "white", " - Помощь по командам чита")));

        // Строка с .connect
        chatLineSender.sendChatLine(clientConnection, compressionThreshold, Arrays.asList(new ChatMessage.Extra(false, false, false, false, false, "gray", "  .connect"), new ChatMessage.Extra(false, false, false, false, false, "white", " - Подключить зеркальный клиент в локальной сети")));

        // Строка с .connect <ip>
        chatLineSender.sendChatLine(clientConnection, compressionThreshold, Arrays.asList(new ChatMessage.Extra(false, false, false, false, false, "gray", "  .connect <ip>"), new ChatMessage.Extra(false, false, false, false, false, "white", " - Подключить внешний зеркальный клиент")));

        // Нижняя рамка
        chatLineSender.sendChatLine(clientConnection, compressionThreshold, List.of(new ChatMessage.Extra(true, false, false, false, false, "dark_gray", "╚════════════════════╝")));
    }


    @Override
    public String getHandledCommand() {
        return ".help";
    }
}
