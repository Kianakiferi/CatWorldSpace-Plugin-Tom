package com.catworldspace.plugin.tom.data;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class DatabaseContext {
    private final Plugin plugin;

    public DatabaseContext(Plugin plugin) {
        this.plugin = plugin;
    }



    public ServerInfo GetPlayerLastConnectServer(ProxiedPlayer player) {
       ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> {

       });

       return null;
    }
}
