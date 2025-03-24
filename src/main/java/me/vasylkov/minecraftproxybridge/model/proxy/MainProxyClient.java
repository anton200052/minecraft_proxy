package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;
import java.util.UUID;

@Getter
@Setter
public class MainProxyClient extends ProxyClient {
    private String userName;
    private UUID uuid;

    public MainProxyClient(Socket socket, String hostAddress) {
        super(socket, hostAddress);
    }
}
