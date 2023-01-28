package com.catworldspace.plugin.system.common

import com.catworldspace.plugin.system.common.connection.ServerHelper
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*

object CommandHelper {
    @JvmStatic
    fun createPrefixedMessage(text: String): Array<BaseComponent> {
        return ComponentBuilder("${ChatColor.BLUE}[${ChatColor.GOLD}${ChatColor.BOLD}CWS ${ChatColor.GRAY}- " +
                "${ChatColor.YELLOW}System${ChatColor.BLUE}]: " +
                "${ChatColor.RESET}$text").create()
    }
}