package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;

import java.net.Socket;
import java.util.UUID;

@Getter
public class ProxyClient {
    private final ClientData data;
    private final ClientState state;
    private final ClientConnection connection;

    public ProxyClient(ClientConnection connection) {
        this.connection = connection;
        this.data = new ClientData();
        this.state = new ClientState();
    }

    @Data
    public static class ClientState {
        private PacketState mainProxyState = PacketState.HANDSHAKE;
        private PacketState mirrorProxyState = PacketState.HANDSHAKE;

        private boolean forwardingToMirrorProxyAllowed = false;
        private boolean forwardingFromMirrorProxyAllowed = false;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientData {
        private String userName;
        private String hostAddress;
        private UUID uuid;

        private int mainClientCompressionThreshold = 0;
        private int mirrorClientCompressionThreshold = 0;
    }

    @Data
    @NoArgsConstructor
    public static class ClientConnection {
        private final Object mainClientLock = new Object();
        private final Object mirrorClientLock = new Object();
        private final Object serverLock = new Object();

        private Socket serverSocket;
        private Socket mainClientSocket;
        private Socket mirrorClientSocket;

        public ClientConnection(Socket serverSocket, Socket mainClientSocket) {
            this.serverSocket = serverSocket;
            this.mainClientSocket = mainClientSocket;
        }
    }
}

