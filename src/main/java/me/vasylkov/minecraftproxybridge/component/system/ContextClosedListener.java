package me.vasylkov.minecraftproxybridge.component.system;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShutDownHandler implements DisposableBean {
    private final ProxyConfiguration proxyConfiguration;

    @Override
    public void destroy() throws Exception {
        System.out.println(123);
    }
}
