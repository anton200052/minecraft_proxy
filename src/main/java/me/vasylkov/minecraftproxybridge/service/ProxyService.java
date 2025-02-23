package me.vasylkov.minecraftproxybridge.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.proxy.MainProxyConnector;
import me.vasylkov.minecraftproxybridge.component.proxy.MirroredProxyConnector;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

@Service
@RequiredArgsConstructor
public class ProxyService {
    private final Logger logger;
    private final ProxyConfiguration proxyConfiguration;
    private final MainProxyConnector mainProxyConnector;
    private final MirroredProxyConnector mirroredProxyConnector;
    private ServerSocket localServerSocket;

    public void enableProxy(ProxyInfo proxyInfo) throws IOException {
        if (proxyConfiguration.isEnabled()) {
            return;
        }
        proxyConfiguration.setEnabled(true);
        proxyConfiguration.setProxyInfo(proxyInfo);

        localServerSocket = new ServerSocket(proxyInfo.getProxyPort());
        ServerSocket mirrorServerSocket = new ServerSocket(25567);
        logger.info("Starting proxy server on port {}", proxyInfo.getProxyPort());

        mainProxyConnector.waitForClientConnectionAndStartDataForwarding(localServerSocket, proxyInfo.getTargetServerAddress(), proxyInfo.getTargetServerPort());
        mirroredProxyConnector.waitForClientConnectionAndStartDataForwarding(mirrorServerSocket);
    }

    public void disableProxy() throws IOException {
        if (!proxyConfiguration.isEnabled()) {
            return;
        }
        proxyConfiguration.setEnabled(false);

        if (localServerSocket != null && !localServerSocket.isClosed()) {
            localServerSocket.close();
        }

        logger.info("Stopping proxy server");
    }

}
