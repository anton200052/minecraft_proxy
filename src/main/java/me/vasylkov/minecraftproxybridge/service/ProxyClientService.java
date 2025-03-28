package me.vasylkov.minecraftproxybridge.service;

import me.vasylkov.minecraftproxybridge.entity.ProxyClient;

public interface ProxyClientService {
    ProxyClient saveClientIfNotPresent(ProxyClient proxyClient);
}
