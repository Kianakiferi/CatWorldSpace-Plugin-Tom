package com.catworldspace.plugin.system.common

import RedirectResult
import com.catworldspace.plugin.system.common.CommandHelper.getServerByName
import com.catworldspace.plugin.system.models.redirect.RedirectResultEnum
import net.md_5.bungee.api.connection.ProxiedPlayer

object RedirectHelper {
    private const val namePrefixDivider = "."
    private const val uuidPrefix = "00000000-0000-0000-"

    @JvmStatic
    fun tryLoginIn(player: ProxiedPlayer): RedirectResult {
        val name = player.name

        val prefixIndex = name.indexOf(namePrefixDivider)
        if (prefixIndex == -1) {  // 未找到前缀分隔符
            return RedirectResult(RedirectResultEnum.PLAYER_NO_PREFIX)
        }

        if (!player.uniqueId.toString().startsWith(uuidPrefix)) {  // UUID 未以指定字符串开头
            return RedirectResult(RedirectResultEnum.PLAYER_UUID_VERIFY_FAIL)
        }

        val prefix = name.substring(0, prefixIndex)
        val server = getServerByName(prefix) ?: return RedirectResult(  // 未找到指定名称的服务器
            RedirectResultEnum.NO_PREFIX_MATCHED_SERVER
        )

        //player.connect(server)
        return RedirectResult(RedirectResultEnum.OK, server)
    }
}