package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@Setter
public class ProxyConnection {
    private ServerData serverData;
    private MainProxyClient mainProxyClient;
    private MirrorProxyClient mirrorProxyClient;

    public ProxyConnection(ServerData serverData, MainProxyClient mainProxyClient) {
        this.serverData = serverData;
        this.mainProxyClient = mainProxyClient;
    }
}
