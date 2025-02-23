package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
@RequiredArgsConstructor
public class PacketRawDataSender {

    public void sendPacketDataToMainClient(ProxyConnection connection, byte[] packetData) throws IOException {
        Socket clientSocket = connection.getMainProxyClient().getSocket();
        if (clientSocket != null && !clientSocket.isClosed()) {
            synchronized (connection.getMainProxyClient().getSocketLock()) {
                writeData(packetData, clientSocket.getOutputStream());
            }
        }
    }

    public void sendPacketDataToMirrorClient(ProxyConnection connection, byte[] packetData) throws IOException {
        Socket mirrorSocket = connection.getMirrorProxyClient().getSocket();
        if (mirrorSocket != null && !mirrorSocket.isClosed()) {
            synchronized (connection.getMirrorProxyClient().getSocketLock()) {
                writeData(packetData, mirrorSocket.getOutputStream());
            }
        }
    }

    public void sendPacketDataToServer(ProxyConnection connection, byte[] packetData) throws IOException {
        Socket serverSocket = connection.getServerData().getSocket();
        if (serverSocket != null && !serverSocket.isClosed()) {
            synchronized (connection.getServerData().getSocketLock()) {
                writeData(packetData, serverSocket.getOutputStream());
            }
        }
    }

    private void writeData(byte[] data, OutputStream outputStream) throws IOException {
        outputStream.write(data);
        outputStream.flush();
    }
}
