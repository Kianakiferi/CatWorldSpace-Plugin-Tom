package com.catworldspace.plugin.system.common.connection

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*

object PlayerHelper {
    @JvmStatic
    fun getPlayerByName(name: String?): ProxiedPlayer? {
        return ProxyServer.getInstance().getPlayer(name)
    }

    @JvmStatic
    fun getPlayerByUUID(uuid: UUID): ProxiedPlayer? {
        return ProxyServer.getInstance().getPlayer(uuid)
    }
}