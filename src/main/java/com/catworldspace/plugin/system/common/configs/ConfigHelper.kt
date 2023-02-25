package com.catworldspace.plugin.system.common.configs

import com.catworldspace.plugin.system.commands.SystemCommand
import com.catworldspace.plugin.system.common.Constants
import com.catworldspace.plugin.system.common.Strings
import com.catworldspace.plugin.system.common.Variables
import com.catworldspace.plugin.system.common.connection.ServerHelper
import com.catworldspace.plugin.system.events.PostLoginEventHandler
import com.catworldspace.plugin.system.exception.IncorrectConfigurationException
import com.catworldspace.plugin.system.exception.ResourceNotFoundException
import com.catworldspace.plugin.system.models.CWSServer
import net.md_5.bungee.config.Configuration

object ConfigHelper {
    @JvmStatic
    fun getLanguageCode(config: Configuration): String {
        val language = config.getString(Constants.ConfigPaths.language)
        if (language.isNullOrEmpty()) {
            throw ResourceNotFoundException("${Strings.ConsolePrefix} ${Strings.LanguageResourceNotFoundExceptionMessage}")
        }
        return language
    }

    @JvmStatic
    fun getServerList(rawList: List<Any?>): List<CWSServer> {
        val result = mutableListOf<CWSServer>()

        for (element in rawList) {
            if (element is LinkedHashMap<*, *>) {
                val key = element.keys.first().toString()
                val value = element.values.first() as LinkedHashMap<*, *>

                if (value.containsKey(Constants.ConfigPaths.ServerElement.permission) && value.containsKey(Constants.ConfigPaths.ServerElement.redirectPrefix)) {
                    val permission = value[Constants.ConfigPaths.ServerElement.permission] as String?
                    val redirectPrefix = value[Constants.ConfigPaths.ServerElement.redirectPrefix] as String?

                    result.add(CWSServer(key, permission, redirectPrefix, ServerHelper.getServerByName(key)))
                } else {
                    throw IncorrectConfigurationException("${Strings.ConsolePrefix} ${Strings.IncorrectConfigurationExceptionMessage} at $key")
                }
            }
        }
        return result
    }

    @JvmStatic
    fun getCommandAndEvents(config: Configuration, strings: Any):List<Any> {
        val result = mutableListOf<Any>()

        val systemCommandName = config.getString("${Constants.ConfigPaths.System.systemCommandName}")
        val systemCommandPermission = config.getString("${Constants.ConfigPaths.System.systemCommandPermission}")
        val systemCommandAliasList = config.getStringList("${Constants.ConfigPaths.System.systemCommandAlias}")
        result.add(
            SystemCommand(
                systemCommandName, systemCommandPermission, *systemCommandAliasList.toTypedArray()
            )
        )

        result.add(PostLoginEventHandler())

        return result.toList()
    }
}