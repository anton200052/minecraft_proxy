package me.vasylkov.minecraftproxybridge.component.proxy;

import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectedProxyClientsStorage {
    private final Map<String, ProxyClient> proxyClients = new ConcurrentHashMap<>();

    public void addProxyClient(String hostName, ProxyClient proxyClient) {
        proxyClients.put(hostName, proxyClient);
    }

    public void removeProxyClient(String hostName) {
        proxyClients.remove(hostName);
    }

    public ProxyClient getProxyClient(String hostName) {
        return proxyClients.get(hostName);
    }

    public boolean containsProxyClient(String hostName) {
        return proxyClients.containsKey(hostName);
    }
}
