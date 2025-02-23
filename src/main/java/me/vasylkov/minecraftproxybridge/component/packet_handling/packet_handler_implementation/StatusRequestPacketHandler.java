package me.vasylkov.minecraftproxybridge.component.packet_handling.packet_handler_implementation;

import lombok.RequiredArgsConstructor;
import me.vasylkov.minecraftproxybridge.component.packet_forwarding.ExtraPacketSender;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.Packet;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusRequestPacket;
import me.vasylkov.minecraftproxybridge.model.packet.packet_implementation.StatusResponsePacket;
import me.vasylkov.minecraftproxybridge.model.proxy.ClientType;
import me.vasylkov.minecraftproxybridge.model.proxy.ProxyConnection;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusRequestPacketHandler implements PacketHandler {
    private final ExtraPacketSender extraPacketSender;

    @Override
    public Packet handlePacket(ProxyConnection proxyConnection, Packet packet, ClientType clientType) {
        if (clientType == ClientType.MIRROR) {
            StatusResponsePacket.MinecraftServerInfo info = new StatusResponsePacket.MinecraftServerInfo(
                    false,
                    false,
                    new StatusResponsePacket.MinecraftServerInfo.Description("A Minecraft Server"),
                    new StatusResponsePacket.MinecraftServerInfo.Players(20, 0),
                    new StatusResponsePacket.MinecraftServerInfo.Version("Paper 1.19.2", 760)
            );
            StatusResponsePacket statusResponse = new StatusResponsePacket(0, info);
            extraPacketSender.sendExtraPacketToMirrorClient(
                    proxyConnection,
                    statusResponse,
                    proxyConnection.getMirrorProxyClient().getCompressionThreshold()
                                                           );
        }
        return packet;
    }

    @Override
    public Class<? extends Packet> getHandledPacketClass() {
        return StatusRequestPacket.class;
    }
}
