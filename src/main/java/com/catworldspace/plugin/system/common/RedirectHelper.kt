package com.catworldspace.plugin.system.common

import com.catworldspace.plugin.system.common.connection.ServerHelper.getServerByName
import com.catworldspace.plugin.system.models.redirect.RedirectResult
import com.catworldspace.plugin.system.models.redirect.RedirectResultEnum
import net.md_5.bungee.api.connection.ProxiedPlayer

object RedirectHelper {
    private const val namePrefixDivider = "."
    private const val uuidPrefix = "00000000-0000-0000-"

    @JvmStatic
    fun tryLoginIn(player: ProxiedPlayer): RedirectResult {
        val name = player.name

        val prefixIndex = name.indexOf(namePrefixDivider)
        if (prefixIndex == -1) { // 如果未找到
            return RedirectResult(RedirectResultEnum.PLAYER_NO_PREFIX)
        }

        if (!player.uniqueId.toString().startsWith(uuidPrefix)) {
            return RedirectResult(RedirectResultEnum.PLAYER_UUID_VERIFY_FAIL)
        }

        val prefix = name.substring(0, prefixIndex)
        val server = getServerByName(prefix) ?: return RedirectResult(
            RedirectResultEnum.NO_PREFIX_MATCHED_SERVER
        )

        //player.connect(server)
        return RedirectResult(RedirectResultEnum.OK, server)
    }
}