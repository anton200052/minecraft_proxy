package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;

import java.net.Socket;

@Getter
@Setter
public abstract class ProxyClient {
    private PacketState packetState;
    private Socket socket;
    private String hostAddress;

    private final Object socketLock;

    protected ProxyClient(Socket socket, String hostAddress) {
        this.socket = socket;
        this.hostAddress = hostAddress;
        this.socketLock = new Object();
        packetState = PacketState.HANDSHAKE;
    }
}
