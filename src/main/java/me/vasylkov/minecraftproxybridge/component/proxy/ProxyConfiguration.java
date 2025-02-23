package me.vasylkov.minecraftproxybridge.component.proxy;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyInfo;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProxyConfiguration {
    private boolean enabled;
    private ProxyInfo proxyInfo;

    @PostConstruct
    public void init() {
        enabled = false;
    }
}
