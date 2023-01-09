package com.catworldspace.plugin.system.common

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*

object CommandHelper {

    private val logger = ProxyServer.getInstance().logger

    @JvmStatic
    fun createMessage(text: String): Array<BaseComponent> {
        return ComponentBuilder("${ChatColor.BLUE}[${ChatColor.GOLD}${ChatColor.BOLD}CWS ${ChatColor.GRAY}- " +
                "${ChatColor.YELLOW}System${ChatColor.BLUE}]: " +
                "${ChatColor.RESET}$text").create()
    }

    @JvmStatic
    val consolePrefix: String = "[CWS - System]:"


    @JvmStatic
    fun getPlayerByName(name: String?): ProxiedPlayer? {
        return ProxyServer.getInstance().getPlayer(name)
    }

    @JvmStatic
    fun getPlayerByUUID(uuid: UUID): ProxiedPlayer? {
        return ProxyServer.getInstance().getPlayer(uuid)
    }

    @JvmStatic
    fun isPlayerInServer(player: ProxiedPlayer, serverName: String): Boolean {
        return player.server.info.name.equals(serverName, ignoreCase = true)
    }

    @JvmStatic
    fun getServerByName(name: String): ServerInfo? {
        return ProxyServer.getInstance().serversCopy?.get(name)
    }
}