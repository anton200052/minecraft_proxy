package me.vasylkov.minecraftproxybridge.component.commands_handling.handler_impl;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools.CommandHandlerKey;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.entity.ProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.MainProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.service.ProxyClientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler {
    private final ProxyClientService proxyClientService;
    private final ProxyConfiguration proxyConfiguration;

    @Override
    public void handleCommand(ProxyConnection proxyConnection, String command, String[] args) {
        if (args.length > 0) {
            MainProxyClient mainProxyClient = proxyConnection.getMainProxyClient();
            String userName = mainProxyClient.getUserName();
            String hostAddress = mainProxyClient.getHostAddress();
            String serverAddress = proxyConfiguration.getProxyInfo().getTargetServerAddress();
            String inGamePassword = args[0];

            proxyClientService.saveClientIfNotPresent(new ProxyClient(userName, hostAddress, serverAddress, inGamePassword));
        }
    }

    @Override
    public List<CommandHandlerKey> getHandledCommandKeys() {
        return List.of(new CommandHandlerKey("/login",
                                             ServerVersion.V1_16_5),
                       new CommandHandlerKey("/l",
                                             ServerVersion.V1_16_5),
                       new CommandHandlerKey("/login",
                                             ServerVersion.V1_19_2),
                       new CommandHandlerKey("/l",
                                             ServerVersion.V1_19_2));
    }
}
