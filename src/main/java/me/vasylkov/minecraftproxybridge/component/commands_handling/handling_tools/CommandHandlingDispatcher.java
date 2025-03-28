package me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl.CommandHandler;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandHandlingDispatcher {
    private final Map<CommandHandlerKey, CommandHandler> handlers = new HashMap<>();

    public CommandHandlingDispatcher(List<CommandHandler> handlerList) {
        for (CommandHandler handler : handlerList) {
            for (CommandHandlerKey commandHandlerKey : handler.getHandledCommandKeys()) {
                handlers.put(commandHandlerKey, handler);
            }
        }
    }

    public void handleCommand(ProxyConnection proxyConnection, String command, String[] args) {
        ServerVersion serverVersion = proxyConnection.getServerData().getServerVersion();
        CommandHandlerKey commandHandlerKey = new CommandHandlerKey(command, serverVersion);
        CommandHandler handler = handlers.get(commandHandlerKey);

        if (handler != null) {
            handler.handleCommand(proxyConnection, command, args);
        }
    }
}
