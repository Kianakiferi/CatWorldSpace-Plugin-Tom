package com.catworldspace.plugin.system.models.redirect

import net.md_5.bungee.api.config.ServerInfo

data class RedirectResult(val resultEnum: RedirectResultEnum, val server: ServerInfo? = null)
