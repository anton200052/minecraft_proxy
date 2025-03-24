package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusRequestPacketV760;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusResponsePacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusResponsePacketV760;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
public class StatusRequestPacketHandlerV760 extends StatusRequestPacketHandler {
    public StatusRequestPacketHandlerV760(ExtraPacketSender extraPacketSender) {
        super(extraPacketSender);
    }

    @Override
    public void sendSpecificStatusResponsePacket(ProxyConnection proxyConnection, int compressionThreshold) {
        StatusResponsePacket.MinecraftServerInfo info = new StatusResponsePacket.MinecraftServerInfo(
                false,
                false,
                new StatusResponsePacket.MinecraftServerInfo.Description("A Minecraft Server"),
                new StatusResponsePacket.MinecraftServerInfo.Players(20, 0),
                new StatusResponsePacket.MinecraftServerInfo.Version("Paper 1.19.2", 760)
        );

        StatusResponsePacketV760 statusResponsePacket = new StatusResponsePacketV760(0, info);
        extraPacketSender.sendExtraPacketToMirrorClient(proxyConnection, statusResponsePacket, compressionThreshold);
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return StatusRequestPacketV760.class;
    }
}
