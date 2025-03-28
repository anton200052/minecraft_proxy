package me.vasylkov.minecraftproxybridge.component.packet_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatCommand;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ChatCommandParser {
    public ChatCommand parseChatCommand(String message) {
        String[] parts = message.trim().split("\\s+");
        String command = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        return new ChatCommand(command, args);
    }
}
