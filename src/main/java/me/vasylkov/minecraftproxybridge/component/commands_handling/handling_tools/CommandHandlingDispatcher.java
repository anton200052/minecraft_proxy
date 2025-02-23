package me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl.CommandHandler;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandHandlingDispatcher {
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandHandlingDispatcher(List<CommandHandler> handlerList) {
        handlerList.forEach(handler -> handlers.put(handler.getHandledCommand(), handler));
    }

    public void handleCommand(ProxyConnection proxyConnection, String command, String[] args) {
        CommandHandler handler = handlers.get(command);

        if (handler != null) {
            handler.handleCommand(proxyConnection, command, args);
        }
    }
}
