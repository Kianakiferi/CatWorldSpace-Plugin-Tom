package com.catworldspace.plugin.system.event;

import com.catworldspace.plugin.system.common.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.logging.Logger;

public class PostLoginEventHandler implements Listener {

    // TODO: 读取自 config.yml 的值

    private final boolean IsDebug = true;
    private final String namePrefixDivider = ".";
    private final String uuidPrefix = "00000000-0000-0000-";
    private final String server1Name = "LoginServer";
    private final String server2Name = "LoginServer_be";

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        Logger logger = ProxyServer.getInstance().getLogger();

        ProxiedPlayer player = event.getPlayer();

        UUID uuid = player.getUniqueId();
        String name = player.getName();

        // TODO：动态配置, 将文本移至本地化
        int prefixIndex = name.indexOf(namePrefixDivider);
        if (prefixIndex == -1) { // 未找到前缀分隔符
            if (IsDebug) {
                logger.info(CommandHelper.GetStringWithPrefix("玩家 " + player.getName() + " 未被分配前缀"));
            }
        } else {
            if (!uuid.toString().startsWith(uuidPrefix)) { // UUID 未以指定字符串开头
                if (IsDebug) {
                    logger.info(CommandHelper.GetStringWithPrefix("玩家 " + player.getName() + " UUID验证失败"));
                }
            } else {
                String prefix = name.substring(0, prefixIndex);
                //if (IsDebug) {
                //    logger.info(CommandHelper.GetTextWithPrefix("玩家 "+ player.getName() + " 的前缀为 " + prefix));
                //}

                ServerInfo server;
                // TODO: 使用配置中的设置
                if (prefix == server2Name) {
                    server = CommandHelper.GetServerByName(server2Name);
                    return;
                } else {
                    server = CommandHelper.GetServerByName(prefix);
                }

                if (server == null) {
                    if (IsDebug) {
                        logger.warning("未找到与前缀 " + prefix + " 匹配的服务器");
                    }
                    player.disconnect(CommandHelper.CreateMessage(ChatColor.RED + "未找到匹配的服务器."));
                } else {
                    CommandHelper.ConnectPlayerTo(player,server);
                }
            }
        }

        if (IsDebug) {
            CommandHelper.ConnectPlayerTo(player,CommandHelper.GetServerByName(server1Name));
        } else {
            CommandHelper.DisconnectPlayer(player,CommandHelper.CreateMessage(ChatColor.RED + "你不在服务器白名单上!"));
        }
    }


}

