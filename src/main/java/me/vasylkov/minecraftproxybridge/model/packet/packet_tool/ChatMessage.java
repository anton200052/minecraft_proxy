package me.vasylkov.minecraftproxybridge.model.packet.packet_tool;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatMessage {
    private List<Extra> extra;
    private String text;

    @Data
    @AllArgsConstructor
    public static class Extra {
        private boolean bold;
        private boolean italic;
        private boolean underlined;
        private boolean strikethrough;
        private boolean obfuscated;
        private String color;
        private String text;
    }
}
