package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProxyInfo {
    private int proxyPort;
    private String targetServerAddress;
    private int targetServerPort;
}
