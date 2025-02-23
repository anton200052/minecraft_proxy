package me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.SystemChatMessagePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemChatLineSender {
    private final ExtraPacketSender extraPacketSender;

    public void sendChatLine(ProxyConnection proxyConnection, int compressionThreshold, List<ChatMessage.Extra> extras) {
        ChatMessage chatMessage = new ChatMessage(extras, "");
        SystemChatMessagePacket packet = new SystemChatMessagePacket(98, chatMessage, false);
        extraPacketSender.sendExtraPacketToMainClient(proxyConnection, packet, compressionThreshold);
    }
}
