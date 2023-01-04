package com.catworldspace.plugin.tom.common;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandHelper {
    private static CommandHelper instance = new CommandHelper();

    public CommandHelper() {
    }
    public static CommandHelper getInstance() {
        if (instance == null) {
            instance = new CommandHelper();
        }
        return instance;
    }

    public static BaseComponent[] CreateMessage(String text) {
        return new ComponentBuilder(text).create();
    }

    public static ProxiedPlayer GetPlayerByName(String name) {
        return ProxyServer.getInstance().getPlayer(name);
    }

    public static ProxiedPlayer GetPlayerByUUID(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid);
    }

    public static boolean IsPlayerInServer(@NotNull ProxiedPlayer player, String serverName) {
        return player.getServer().getInfo().getName().equalsIgnoreCase(serverName);
    }

    public static ServerInfo GetServerByName(String name) {
        return ProxyServer.getInstance().getServersCopy().getOrDefault(name, null);
    }
}
