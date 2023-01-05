package com.catworldspace.plugin.system;


import com.catworldspace.plugin.system.commands.TomCommand;
import com.catworldspace.plugin.system.event.PostLoginEventHandler;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
public final class Tom extends Plugin {
    //private DatabaseContext context;

    private List<Object> commandsAndEvents;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        PluginManager manager = getProxy().getPluginManager();

        // TODO: 本地化; 可配置
        String configName = "config.yml";

        //var config = TryGetConfig(configName);

        commandsAndEvents = Lists.newArrayList(
                new TomCommand("system", "cws-system.*"),
                new PostLoginEventHandler()
        );

        Register(manager, commandsAndEvents);

        logger.info("插件已加载");
    }


    @Override
    public void onDisable() {
        Logger logger = getLogger();
        PluginManager manager = getProxy().getPluginManager();

        UnRegister(manager, commandsAndEvents);

        logger.info("插件已卸载");
    }


    // TODO: 批量注册
    private void Register(PluginManager manager, List<Object> commandsAndEvents) {
        for (Object item : commandsAndEvents) {
            if (item instanceof Command) {
                Command command = (Command) item;
                manager.registerCommand(this, command);
            }
            if (item instanceof Listener) {
                Listener listener = (Listener) item;
                manager.registerListener(this, listener);
            }
        }
    }

    private void UnRegister(PluginManager manager, List<Object> commandsAndEvents) {
        for (Object item : commandsAndEvents) {
            if (item instanceof Command) {
                Command command = (Command) item;
                manager.unregisterCommand(command);
            }
            if (item instanceof Listener) {
                Listener listener = (Listener) item;
                manager.unregisterListener(listener);
            }
        }
    }

    private Configuration TryGetConfig(String configName) {
        ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        try {
            return provider.load(TryGetConfigFile(configName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private File TryGetConfigFile(String configName) {
        File folder = TryGetDataFolder();
        File file = new File(folder, configName);

        if (!file.exists()) {
            try (InputStream input = getResourceAsStream(configName)) {
                Files.copy(input, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private File TryGetDataFolder() {
        File folder = getDataFolder();
        if (!folder.exists()) {
            try {
                if (!folder.mkdir()) {
                    throw new IOException("无法创建插件文件夹");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return folder;
    }
}
