package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlerKey;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;

import java.util.List;

public interface CommandHandler {
    void handleCommand(ProxyConnection proxyConnection, String command, String[] args);
    List<CommandHandlerKey> getHandledCommandKeys();
}
