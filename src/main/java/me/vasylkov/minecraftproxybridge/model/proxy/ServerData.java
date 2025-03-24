package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;

import java.net.Socket;

@Getter
@Setter
public class ServerData {
    private final Socket socket;
    private final Object socketLock;

    private ServerVersion serverVersion;
    private int compressionThreshold;

    public ServerData(Socket socket) {
        this.socket = socket;
        socketLock = new Object();
        this.serverVersion = ServerVersion.UNKNOWN;
    }
}
