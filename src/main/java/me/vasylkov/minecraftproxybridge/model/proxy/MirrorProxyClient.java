package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@Setter
public class MirrorProxyClient extends ProxyClient {
    private boolean forwardingToMirrorProxyAllowed;
    private boolean forwardingFromMirrorProxyAllowed;

    public MirrorProxyClient(Socket socket, String hostAddress) {
        super(socket, hostAddress, new Object());

        forwardingFromMirrorProxyAllowed = false;
        forwardingToMirrorProxyAllowed = false;
    }
}
