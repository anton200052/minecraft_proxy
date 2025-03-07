package me.vasylkov.minecraftproxybridge.component.proxy;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.PacketForwarder;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@RequiredArgsConstructor
public class MirroredProxyConnector {
    private final Logger logger;
    private final ProxyConfiguration proxyConfiguration;
    private final PacketForwarder packetForwarder;

    @Async
    public void waitForClientConnectionAndStartDataForwarding(ServerSocket localServerSocket) {
        while (proxyConfiguration.isEnabled()) {
            try {
                Socket clientSocket = localServerSocket.accept();
                String clientHostAddress = clientSocket.getLocalAddress().getHostAddress();
                logger.info("Подключен зеркальный клиент: {}", clientHostAddress);

                ProxyConnection proxyConnection = ProxyConnection.builder().mirrorProxyClient(new MirrorProxyClient(clientSocket, clientHostAddress)).build();

                packetForwarder.forwardDataFromMirrorClient(proxyConnection);
            }
            catch (IOException e) {
                logger.error("Ошибка при установлении соединения с клиентом: {}", e.getMessage());
            }
        }
    }
}
