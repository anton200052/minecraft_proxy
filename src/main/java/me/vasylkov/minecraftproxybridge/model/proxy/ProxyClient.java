package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.PacketState;

import java.net.Socket;

@Getter
@Setter
public abstract class ProxyClient {
    private PacketState packetState;
    private int compressionThreshold;
    private Socket socket;
    private String hostAddress;

    private final Object socketLock;

    protected ProxyClient(Socket socket, String hostAddress, Object socketLock) {
        this.socket = socket;
        this.hostAddress = hostAddress;
        this.socketLock = socketLock;
        packetState = PacketState.HANDSHAKE;
    }
}
