package me.vasylkov.minecraftproxybridge.service;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.entity.ProxyClient;
import me.vasylkov.minecraftproxybridge.repository.ProxyClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProxyClientServiceImp implements ProxyClientService {
    private final ProxyClientRepository proxyClientRepository;

    @Override
    public ProxyClient saveClientIfNotPresent(ProxyClient proxyClient) {
        String userName = proxyClient.getUserName();
        String serverAddress = proxyClient.getServerAddress();
        String inGamePassword = proxyClient.getInGamePassword();

        Optional<ProxyClient> existingClient = proxyClientRepository.findByUserNameAndServerAddressAndInGamePassword(userName, serverAddress, inGamePassword);
        if (existingClient.isEmpty()) {
            return proxyClientRepository.save(proxyClient);
        }
        return proxyClient;
    }
}
