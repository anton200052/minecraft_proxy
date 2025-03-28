package me.vasylkov.minecraftproxybridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "proxy_clients")
public class ProxyClient {
    public ProxyClient(String userName, String hostAddress, String serverAddress, String inGamePassword) {
        this.userName = userName;
        this.hostAddress = hostAddress;
        this.serverAddress = serverAddress;
        this.inGamePassword = inGamePassword;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "host_address")
    private String hostAddress;

    @Column(name = "server_address")
    private String serverAddress;

    @Column(name = "ingame_password")
    private String inGamePassword;
}
