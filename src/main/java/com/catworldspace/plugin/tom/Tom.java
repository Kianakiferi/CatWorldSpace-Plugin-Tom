package com.catworldspace.plugin.tom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import com.catworldspace.plugin.tom.common.CommandHelper;
import com.catworldspace.plugin.tom.data.DatabaseContext;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import com.catworldspace.plugin.tom.commands.TomCommand;
import com.catworldspace.plugin.tom.commands.RedirectCommand;
import com.catworldspace.plugin.tom.event.PostLoginEventHandler;

public final class Tom extends Plugin {
    private DatabaseContext context;

    private CommandHelper helper;

    @Override
    public void onEnable() {
        var logger = getLogger();
        var manager = getProxy().getPluginManager();

        // TODO: 本地化; 可配置
        var configName = "config.yml";

        var config = TryGetConfig(configName);


        var commandsAndEvents = List.of(new TomCommand("tom", "cws-tom.*"), new RedirectCommand("redirect", "cws-tom.redirect", "goto"), new PostLoginEventHandler());

        Regist(manager, commandsAndEvents);


        logger.info("Hello Waterfall!");
    }


    @Override
    public void onDisable() {
        var logger = getLogger();
        var manager = getProxy().getPluginManager();


        logger.info("Plugins disabled");
    }


    private Configuration TryGetConfig(String configName) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(TryGetConfigFile(configName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    // TODO: 批量注册
    private void Regist(PluginManager manager, List<Object> commandsAndEvents) {
        for (object item : commandsAndEvents) {

        }

        manager.registerCommand(this, new TomCommand());

        manager.registerListener(this, new PostLoginEventHandler());

    }

    private void UnRegist(PluginManager manager, List<Object> commandsAndEvents) {

    }

    private File TryGetConfigFile(String configName) {
        var folder = TryGetDataFolder();
        var file = new File(folder, configName);

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
        var folder = getDataFolder();
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
