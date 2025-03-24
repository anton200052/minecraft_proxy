package me.vasylkov.minecraftproxybridge.component.commands_handling.handling_tools;

import me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core.ServerVersion;

public record CommandHandlerKey(String command, ServerVersion serverVersion) {}

