package me.vasylkov.minecraftproxybridge.component.proxy;

import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectedProxyConnections {
    private final Map<String, ProxyConnection> proxyClients = new ConcurrentHashMap<>();

    public void addProxyClient(String username, ProxyConnection proxyConnection) {
        proxyClients.put(username, proxyConnection);
    }

    public void removeProxyConnection(String username) {
        proxyClients.remove(username);
    }

    public ProxyConnection getProxyConnection(String username) {
        return proxyClients.get(username);
    }

    public boolean containsProxyConnection(String username) {
        return proxyClients.containsKey(username);
    }
}
