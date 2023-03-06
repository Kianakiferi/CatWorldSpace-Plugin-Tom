package com.catworldspace.plugin.system.common

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder

object CommandHelper {
    @JvmStatic
    fun createPrefixedMessage(text: String): Array<BaseComponent> {
        return ComponentBuilder("${ChatColor.BLUE}[${ChatColor.GOLD}${ChatColor.BOLD}CWS ${ChatColor.GRAY}- " +
                "${ChatColor.YELLOW}System${ChatColor.BLUE}]: " +
                "${ChatColor.RESET}$text").create()
    }
}