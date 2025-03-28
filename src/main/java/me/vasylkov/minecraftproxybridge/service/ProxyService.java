package me.vasylkov.minecraftproxybridge.service;

import me.vasylkov.minecraftproxybridge.model.proxy.ProxyInfo;

import java.io.IOException;

public interface ProxyService {
    void enableProxy(ProxyInfo proxyInfo) throws IOException;
    void disableProxy() throws IOException;
}
