package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;

public interface CommandHandler {
    void handleCommand(ProxyClient proxyClient, String command, String[] args);
    String getHandledCommand();
}
