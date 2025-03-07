package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProxyConnection {
    private ServerData serverData;
    private MainProxyClient mainProxyClient;
    private MirrorProxyClient mirrorProxyClient;
}
