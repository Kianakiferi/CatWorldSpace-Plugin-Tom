package com.catworldspace.plugin.system

import com.catworldspace.plugin.system.common.program.Strings
import com.catworldspace.plugin.system.common.configs.ConfigHelper
import com.catworldspace.plugin.system.common.program.PluginHelper

import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.PluginManager

class System : Plugin() {
    private var commandsAndEvents: List<Any>? = null

    override fun onEnable() {
        val logger = logger

        val pluginFolder = PluginHelper.getOrCreateFolder(dataFolder)

        val config = PluginHelper.getConfiguration(pluginFolder)
        val strings = PluginHelper.getLocalizedStrings(pluginFolder, ConfigHelper.getLanguageCode(config))

        PluginHelper.setVariables(config)
        PluginHelper.setStrings(strings)

        commandsAndEvents = ConfigHelper.getCommandAndEvents(config)
        register(proxy.pluginManager, commandsAndEvents)

        logger.info(Strings.pluginLoadedMessage)
    }
    override fun onDisable() {
        val logger = logger

        unRegister(proxy.pluginManager, commandsAndEvents)
        logger.info(Strings.pluginUnloadedMessage)
    }

    private fun register(manager: PluginManager, commandsAndEvents: Collection<Any>?) {
        for (item in commandsAndEvents!!) {
            if (item is Command) {
                manager.registerCommand(this, item)
            }
            if (item is Listener) {
                manager.registerListener(this, item)
            }
        }
    }

    private fun unRegister(manager: PluginManager, commandsAndEvents: Collection<Any>?) {
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
