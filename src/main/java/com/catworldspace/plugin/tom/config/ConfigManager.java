package com.catworldspace.plugin.tom.config;

public class ConfigManager {
    private static ConfigManager instance;
    private Config config;

    private ConfigManager() {
        config = new Config();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
}
