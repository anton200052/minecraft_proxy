package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlerKey;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;

public interface CommandHandler {
    void handleCommand(ProxyConnection proxyConnection, String command, String[] args);
    CommandHandlerKey getHandledCommandKey();
}
