package me.vasylkov.minecraftproxybridge.service;

import jakarta.annotation.PreDestroy;
import me.vasylkov.minecraftproxybridge.component.proxy.MainProxyConnector;
import me.vasylkov.minecraftproxybridge.component.proxy.MirroredProxyConnector;
import me.vasylkov.minecraftproxybridge.component.proxy.ProxyConfiguration;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

@Service
public class ProxyServiceImp implements ProxyService {
    private final Logger logger;
    private final ProxyConfiguration proxyConfiguration;
    private final MainProxyConnector mainProxyConnector;
    @Qualifier("mirroredProxyConnector")
    private final MirroredProxyConnector mirroredProxyConnector;
    private ServerSocket localServerSocket;
    private ServerSocket mirrorServerSocket;

    public ProxyServiceImp(Logger logger, ProxyConfiguration proxyConfiguration, MainProxyConnector mainProxyConnector, MirroredProxyConnector mirroredProxyConnector) {
        this.logger = logger;
        this.proxyConfiguration = proxyConfiguration;
        this.mainProxyConnector = mainProxyConnector;
        this.mirroredProxyConnector = mirroredProxyConnector;
    }

    @Override
    public void enableProxy(ProxyInfo proxyInfo) throws IOException {
        if (proxyConfiguration.isEnabled()) {
            return;
        }

        proxyConfiguration.setEnabled(true);
        proxyConfiguration.setProxyInfo(proxyInfo);

        localServerSocket = new ServerSocket(proxyInfo.getMainProxyPort());
        mirrorServerSocket = new ServerSocket(proxyInfo.getMirrorProxyPort());
        logger.info("Starting proxy server on port {}", proxyInfo.getMainProxyPort());

        mainProxyConnector.waitForClientConnectionAndStartDataForwarding(localServerSocket, proxyInfo.getTargetServerAddress(), proxyInfo.getTargetServerPort());
        mirroredProxyConnector.waitForClientConnectionAndStartDataForwarding(mirrorServerSocket, proxyInfo.getTargetServerAddress(), proxyInfo.getTargetServerPort());
    }

    @Override
    public void disableProxy() throws IOException {
        if (!proxyConfiguration.isEnabled()) {
            return;
        }

        proxyConfiguration.setEnabled(false);

        if (localServerSocket != null && !localServerSocket.isClosed()) {
            localServerSocket.close();
        }

        if (mirrorServerSocket != null && !mirrorServerSocket.isClosed()) {
            mirrorServerSocket.close();
        }

        logger.info("Stopping proxy server");
    }
}
