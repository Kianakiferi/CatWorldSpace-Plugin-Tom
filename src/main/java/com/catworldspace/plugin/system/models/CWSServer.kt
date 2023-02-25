package com.catworldspace.plugin.system.models

import net.md_5.bungee.api.config.ServerInfo


data class CWSServer(val ServerName: String, val Permission: String?, val RedirectPrefix:String?, var ServerInfo: ServerInfo? = null)