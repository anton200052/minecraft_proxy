package me.vasylkov.minecraftproxybridge.component.proxy;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.PacketForwarder;
import me.vasylkov.minecraftproxybridge.model.proxy.MirrorProxyClient;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import me.vasylkov.minecraftproxybridge.model.proxy.ServerData;
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
    public void waitForClientConnectionAndStartDataForwarding(ServerSocket localServerSocket, String targetServerAddress, int targetServerPort) {
        while (proxyConfiguration.isEnabled()) {
            try {
                Socket clientSocket = localServerSocket.accept();
                String clientHostAddress = clientSocket.getLocalAddress().getHostAddress();
                logger.info("Подключен зеркальный клиент: {}", clientHostAddress);
                Socket serverSocket = new Socket(targetServerAddress, targetServerPort);

                ProxyConnection proxyConnection = ProxyConnection.builder().serverData(new ServerData(serverSocket)).mirrorProxyClient(new MirrorProxyClient(clientSocket, clientHostAddress)).build();
                packetForwarder.forwardDataFromMirrorClient(proxyConnection);
                packetForwarder.forwardDataToMirrorClientUntilMerge(proxyConnection);
            }
            catch (IOException e) {
                logger.error("Ошибка при установлении соединения с клиентом: {}", e.getMessage());
            }
        }
    }
}
