package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.*;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class StatusRequestPacketHandlerV754 extends StatusRequestPacketHandler {
    public StatusRequestPacketHandlerV754(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    public void sendSpecificStatusResponsePacket(ProxyConnection proxyConnection, int compressionThreshold) {
        StatusResponsePacket.MinecraftServerInfo info = new StatusResponsePacket.MinecraftServerInfo(
                false,
                false,
                new StatusResponsePacket.MinecraftServerInfo.Description("A Minecraft Server"),
                new StatusResponsePacket.MinecraftServerInfo.Players(20, 0),
                new StatusResponsePacket.MinecraftServerInfo.Version("Paper 1.16.5", 754)
        );

        StatusResponsePacketV754 statusResponsePacket = new StatusResponsePacketV754(0, info);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, statusResponsePacket, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return StatusRequestPacketV754.class;
    }
}
