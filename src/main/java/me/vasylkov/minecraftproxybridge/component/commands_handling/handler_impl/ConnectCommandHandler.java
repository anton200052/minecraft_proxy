package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.SystemChatLineSender;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectCommandHandler implements CommandHandler {
    private final SystemChatLineSender chatLineSender;

    @Override
    public void handleCommand(ProxyClient proxyClient, String command, String[] args) {

    }

    @Override
    public String getHandledCommand() {
        return ".connect";
    }
}
