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
    private final ConnectedProxyConnections connectedProxyConnections;

    @Async
    public void waitForClientConnectionAndStartDataForwarding(ServerSocket localServerSocket) throws IOException {
        while (proxyConfiguration.isEnabled()) {
            Socket clientSocket = localServerSocket.accept();
            String clientHostAddress = clientSocket.getLocalAddress().getHostAddress();
            logger.info("Подключен зеркальный клиент: {}", clientHostAddress);

            ProxyConnection proxyConnection = connectedProxyConnections.getProxyConnection(clientHostAddress);
            if (proxyConnection != null) {
                proxyConnection.setMirrorProxyClient(new MirrorProxyClient(clientSocket, clientHostAddress));
                packetForwarder.forwardDataFromMirrorClient(proxyConnection);
            }
        }
    }
}
