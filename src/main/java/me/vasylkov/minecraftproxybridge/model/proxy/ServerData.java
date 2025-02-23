package me.vasylkov.minecraftproxybridge.model.proxy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.Socket;

@Getter
@AllArgsConstructor
public class ServerData {
    private final Socket socket;

    private final Object socketLock = new Object();
}
