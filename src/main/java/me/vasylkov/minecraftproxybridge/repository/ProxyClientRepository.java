package me.vasylkov.minecraftproxybridge.repository;

import me.vasylkov.minecraftproxybridge.entity.ProxyClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProxyClientRepository extends JpaRepository<ProxyClient, Long> {
    Optional<ProxyClient> findByUserNameAndServerAddressAndInGamePassword(String userName, String serverAddress, String inGamePassword);
}
