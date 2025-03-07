package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import lombok.Getter;

@Getter
public enum ServerVersion {
    UNKNOWN(0),
    V1_16_5(754),
    V1_19_2(760);

    private final int protocolVersion;

    ServerVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public static ServerVersion fromProtocolVersion(int protocolVersion) {
        for (ServerVersion status : ServerVersion.values()) {
            if (status.getProtocolVersion() == protocolVersion) {
                return status;
            }
        }
        throw new IllegalArgumentException("Нет такого статуса с кодом: " + protocolVersion);
    }
}
