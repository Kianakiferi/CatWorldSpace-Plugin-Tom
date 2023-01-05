package com.catworldspace.plugin.system.common;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;
import java.util.logging.Logger;

public class CommandHelper {
    private static final Logger logger = ProxyServer.getInstance().getLogger();

    private static CommandHelper instance;

    public CommandHelper() {
    }

    public static CommandHelper getInstance() {
        if (instance == null) {
            instance = new CommandHelper();
        }
        return instance;
    }

    public static BaseComponent[] CreateMessage(String text) {
        return new ComponentBuilder("&b[&6&lCWS &7- &eSystem&b]: " + text).create();
    }
    public static String GetStringWithPrefix(String text) {
        return "[CWS - System]: " + text;
    }

    public static ProxiedPlayer GetPlayerByName(String name) {
        return ProxyServer.getInstance().getPlayer(name);
    }
    public static ProxiedPlayer GetPlayerByUUID(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid);
    }
    public static boolean IsPlayerInServer(ProxiedPlayer player, String serverName) {
        return player.getServer().getInfo().getName().equalsIgnoreCase(serverName);
    }

    public static ServerInfo GetServerByName(String name) {
        return ProxyServer.getInstance().getServersCopy().getOrDefault(name, null);
    }

    public static void ConnectPlayerTo(ProxiedPlayer player, ServerInfo server) {
        player.connect(server);
        logger.info(GetStringWithPrefix("已将玩家 "+ player.getName() + " 重定向至 "+ server.getName() + " 服务器."));
    }

    public static void DisconnectPlayer(ProxiedPlayer player, BaseComponent[] reason) {
        player.disconnect(reason);
        logger.info(GetStringWithPrefix("已断开玩家 "+ player.getName() + " 的链接"));
    }

}
