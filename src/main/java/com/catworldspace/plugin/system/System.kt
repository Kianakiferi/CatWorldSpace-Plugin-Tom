package com.catworldspace.plugin.system

import com.catworldspace.plugin.system.commands.SystemCommand
import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.configs.ConfigHelper.getOrCreateFolder
import com.catworldspace.plugin.system.configs.ConfigHelper.getConfiguration
import com.catworldspace.plugin.system.configs.ConfigHelper.getLanguageStrings

import com.catworldspace.plugin.system.events.PostLoginEventHandler
import com.google.common.collect.Lists
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.PluginManager

class System : Plugin() {
    private var commandsAndEvents: List<Any>? = null

    override fun onEnable() {
        val logger = logger

        val pluginFolder = getOrCreateFolder(dataFolder)

        val config = getConfiguration(pluginFolder)

        val language = config.getString(Constants.languagePath)
        val strings = getLanguageStrings(pluginFolder, language)

        commandsAndEvents = Lists.newArrayList(
            SystemCommand("system", "cws-system.*"),
            PostLoginEventHandler()
        )
        register(proxy.pluginManager, commandsAndEvents)
        logger.info("插件已加载")
    }

    override fun onDisable() {
        val logger = logger

        unRegister(proxy.pluginManager, commandsAndEvents)
        logger.info("插件已卸载")
    }

    private fun register(manager: PluginManager, commandsAndEvents: List<Any>?) {
        for (item in commandsAndEvents!!) {
            if (item is Command) {
                manager.registerCommand(this, item)
            }
            if (item is Listener) {
                manager.registerListener(this, item)
            }
        }
    }

    private fun unRegister(manager: PluginManager, commandsAndEvents: List<Any>?) {
        for (item in commandsAndEvents!!) {
            if (item is Command) {
                manager.unregisterCommand(item)
            }
            if (item is Listener) {
                manager.unregisterListener(item)
            }
        }
    }
}
