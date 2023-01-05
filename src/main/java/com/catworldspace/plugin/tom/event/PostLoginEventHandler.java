package com.catworldspace.plugin.tom.event;

import com.catworldspace.plugin.tom.common.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PostLoginEventHandler implements Listener {

    // TODO: 读取自 config.yml 的值
    private final String namePrefixDivider = ".";
    private final String uuidPrefix = "00000000-0000-0000-";

    private final boolean IsDebug = true;

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        UUID uuid = player.getUniqueId();
        String name = player.getName();

        // TODO：动态配置, 将文本移至本地化
        int prefixIndex = name.indexOf(namePrefixDivider);
        if (prefixIndex == -1) { // 未找到前缀分隔符
            player.sendMessage(CommandHelper.CreateMessage(ChatColor.RED + "您未被分配前缀, 请联系管理员"));
            if (IsDebug) {
                return;
            }
        } else {
            if (!uuid.toString().startsWith(uuidPrefix)) {
                player.sendMessage(CommandHelper.CreateMessage(ChatColor.RED + "UUID验证失败, 请联系管理员"));
                if (IsDebug) {
                    return;
                }
            } else {
                String prefix = name.substring(0, prefixIndex);
                if (IsDebug) {
                    player.sendMessage(CommandHelper.CreateMessage("(Debug) 您的前缀为 " + prefix));
                }
                ServerInfo server = CommandHelper.GetServerByName(prefix);
                if (server == null) {
                    player.sendMessage(CommandHelper.CreateMessage(ChatColor.RED + "当前账号没有匹配的服务器, 请联系管理员"));
                    if (IsDebug) {
                        return;
                    }
                } else {
                    player.sendMessage(CommandHelper.CreateMessage(ChatColor.RED + "您的服务器为 " + server.getName()));
                    player.connect(server);
                }
            }


        }

        player.disconnect(new ComponentBuilder(ChatColor.RED + "You are not on the server's whitelist!").create());
    }


}

