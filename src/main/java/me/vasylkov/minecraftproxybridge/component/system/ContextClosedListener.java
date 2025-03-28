package me.vasylkov.minecraftproxybridge.component.system;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.service.ProxyService;
import org.slf4j.Logger;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ContextClosedListener {
    private final ProxyService proxyService;
    private final Logger logger;

    @EventListener(ContextClosedEvent.class)
    public void onContextClosed() {
        try {
            proxyService.disableProxy();
        } catch (IOException e) {
            logger.error("Досрочное завершение программы с ошибкой: {}", e.getMessage());
        }
    }
}
