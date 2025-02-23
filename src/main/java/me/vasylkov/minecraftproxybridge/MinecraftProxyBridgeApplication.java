package me.vasylkov.minecraftproxybridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MinecraftProxyBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinecraftProxyBridgeApplication.class, args);
    }

}
