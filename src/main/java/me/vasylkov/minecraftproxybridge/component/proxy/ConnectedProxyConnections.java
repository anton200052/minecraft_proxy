package me.vasylkov.minecraftproxybridge.component.proxy;

import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectedProxyConnections {
    private final Map<String, ProxyConnection> proxyClients = new ConcurrentHashMap<>();

    public void addProxyClient(String hostName, ProxyConnection proxyConnection) {
        proxyClients.put(hostName, proxyConnection);
    }

    public void removeProxyConnection(String hostName) {
        proxyClients.remove(hostName);
    }

    public ProxyConnection getProxyConnection(String hostName) {
        return proxyClients.get(hostName);
    }

    public boolean containsProxyConnection(String hostName) {
        return proxyClients.containsKey(hostName);
    }
}
