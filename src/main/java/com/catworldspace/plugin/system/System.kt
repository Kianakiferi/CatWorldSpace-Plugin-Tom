package com.catworldspace.plugin.system

import com.catworldspace.plugin.system.commands.SystemCommand
import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.configs.ConfigHelper.getOrCreateFolder
import com.catworldspace.plugin.system.configs.ConfigHelper.getConfiguration
import com.catworldspace.plugin.system.configs.ConfigHelper.getLanguageCode
import com.catworldspace.plugin.system.configs.ConfigHelper.getLanguageStrings
import com.catworldspace.plugin.system.common.Variables

import com.catworldspace.plugin.system.events.PostLoginEventHandler
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.PluginManager
import net.md_5.bungee.config.Configuration

class System : Plugin() {
    private var commandsAndEvents: List<Any>? = null

    override fun onEnable() {
        val logger = logger

        val pluginFolder = getOrCreateFolder(dataFolder)

        val config = getConfiguration(pluginFolder)
        val strings = getLanguageStrings(pluginFolder, getLanguageCode(config))

        setVariables(config, strings)
        commandsAndEvents = getCommandAndEvents(config, strings)
        register(proxy.pluginManager, commandsAndEvents)

        logger.info(Variables.Strings.PluginLoadedMessage)
    }
    override fun onDisable() {
        val logger = logger

        unRegister(proxy.pluginManager, commandsAndEvents)
        logger.info(Variables.Strings.PluginUnloadedMessage)
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

    private fun setVariables(config: Configuration, strings: Any) {
        Variables.IsDebugEnabled = config.getBoolean(Constants.debugPath)

        Variables.PrefixUUIDMatch.IsEnabled = config.getBoolean("${Constants.loginPrefixUUIDMatchPath}.enabled")
        Variables.PrefixUUIDMatch.IsMatchUUIDEnabled = config.getBoolean("${Constants.loginPrefixUUIDMatch_MatchUUIDPath}.enabled")
        Variables.PrefixUUIDMatch.PrefixDivider = config.getString("${Constants.loginPrefixUUIDMatchPath}.prefix-divider")

        Variables.PrefixUUIDMatch.redirectList = config.getList("${Constants.loginPrefixUUIDMatchPath}.redirect-server-list").toList()


    }
    private fun getCommandAndEvents(config: Configuration, strings: Any):List<Any> {
        val result = mutableListOf<Any>()

        val systemCommandName = config.getString("${Constants.commandSystemPath}.name")
        val systemCommandPermission = config.getString("${Constants.commandSystemPath}.permission")
        val systemCommandAliasList = config.getStringList("${Constants.commandSystemPath}.alias")
        result.add(SystemCommand(
            systemCommandName, systemCommandPermission, *systemCommandAliasList.toTypedArray()
        ))

        result.add(PostLoginEventHandler())

        return result.toList()
    }

}
