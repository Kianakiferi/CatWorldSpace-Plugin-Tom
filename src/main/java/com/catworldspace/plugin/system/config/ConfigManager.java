package com.catworldspace.plugin.system.config;

import net.md_5.bungee.api.ProxyServer;

import java.io.File;


public class ConfigManager {
    private static ConfigManager instance;
    private File configFile;

    private ConfigManager() {
        File folder = ProxyServer.getInstance().getPluginsFolder();

    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
}
