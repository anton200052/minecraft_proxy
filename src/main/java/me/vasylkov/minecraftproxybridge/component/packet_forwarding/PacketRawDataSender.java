package me.vasylkov.minecraftproxybridge.component.packet_forwarding;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
@RequiredArgsConstructor
public class PacketRawDataSender {

    public void sendPacketDataToMainClient(ProxyClient.ClientConnection connection, byte[] packetData) throws IOException {
        Socket clientSocket = connection.getMainClientSocket();
        if (clientSocket != null && !clientSocket.isClosed()) {
            synchronized (connection.getMainClientLock()) {
                writeData(packetData, clientSocket.getOutputStream());
            }
        }
    }

    public void sendPacketDataToMirrorClient(ProxyClient.ClientConnection connection, byte[] packetData) throws IOException {
        Socket mirrorSocket = connection.getMirrorClientSocket();
        if (mirrorSocket != null && !mirrorSocket.isClosed()) {
            synchronized (connection.getMirrorClientLock()) {
                writeData(packetData, mirrorSocket.getOutputStream());
            }
        }
    }

    public void sendPacketDataToServer(ProxyClient.ClientConnection connection, byte[] packetData) throws IOException {
        Socket serverSocket = connection.getServerSocket();
        if (serverSocket != null && !serverSocket.isClosed()) {
            synchronized (connection.getServerLock()) {
                writeData(packetData, serverSocket.getOutputStream());
            }
        }
    }

    private void writeData(byte[] data, OutputStream outputStream) throws IOException {
        outputStream.write(data);
        outputStream.flush();
    }
}