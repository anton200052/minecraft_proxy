package me.vasylkov.minecraftproxybridge.model.packet.packet_tool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatCommand {
    private String command;
    private String[] arguments;
}
