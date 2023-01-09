package com.catworldspace.plugin.system.events

import com.catworldspace.plugin.system.common.CommandHelper.createMessage
import com.catworldspace.plugin.system.common.CommandHelper.consolePrefix
import com.catworldspace.plugin.system.common.CommandHelper.getServerByName

import com.catworldspace.plugin.system.models.redirect.RedirectResultEnum
import com.catworldspace.plugin.system.common.RedirectHelper.tryLoginIn
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class PostLoginEventHandler : Listener {
    // TODO: 读取自 config.yml 的值
    private val IsDebug = true

    private val serverName1 = "LoginServer"
    private val serverName2 = "LoginServer_be"

    @EventHandler
    fun onPostLogin(event: PostLoginEvent) {
        val logger = ProxyServer.getInstance().logger

        val player = event.player

        val result = tryLoginIn(player)
        when (result.resultEnum) {
            RedirectResultEnum.PLAYER_NO_PREFIX -> logger.info(
                "$consolePrefix 玩家 ${player.name} 未被分配前缀"
            )

            RedirectResultEnum.PLAYER_UUID_VERIFY_FAIL -> logger.info(
                "$consolePrefix 玩家 ${player.name} UUID 验证失败"
            )

            RedirectResultEnum.NO_PREFIX_MATCHED_SERVER -> logger.warning(
                "$consolePrefix 未找到与前缀匹配的服务器"
            )

            RedirectResultEnum.OK -> {
                if (result.server != null) {
                    player.connect(result.server)
                    logger.info(
                        "$consolePrefix 已将玩家 ${player.name} 连接至 ${result.server.name} 服务器"
                    )
                } else {
                    logger.warning(
                        "$consolePrefix 未预见的错误"
                    )
                }
            }
        }

        if (IsDebug) {
            player.connect(getServerByName(serverName1))
        }
        player.disconnect(*createMessage("${ChatColor.RED} 未找到匹配的服务器, 请联系管理员"))
        logger.info(
            "$consolePrefix 已拒绝玩家 ${player.name} 的连接请求"
        )
    }
}