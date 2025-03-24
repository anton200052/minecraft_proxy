package me.vasylkov.minecraftproxybridge.model.packet.packet_implementation;

import lombok.Getter;
import lombok.Setter;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ByteArrayHelper;
import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.PacketDataCodec;
import me.vasylkov.minecraftproxybridge.model.packet.packet_tool.ChatMessage;

import java.io.IOException;

@Getter
@Setter
public class SystemChatMessagePacketV760 extends SystemChatMessagePacket {
    private boolean overlay;

    public SystemChatMessagePacketV760(int packetId, ChatMessage chatMessage, boolean overlay) {
        super(packetId, chatMessage);
        this.overlay = overlay;
    }

    @Override
    public byte[] toRawData(PacketDataCodec packetDataCodec, ByteArrayHelper byteArrayHelper) throws IOException {
        byte[] baseClassData = super.toRawData(packetDataCodec, byteArrayHelper);
        byte[] overlayBoolean = packetDataCodec.encodeBoolean(this.overlay);

        return byteArrayHelper.merge(baseClassData, overlayBoolean);
    }
}

