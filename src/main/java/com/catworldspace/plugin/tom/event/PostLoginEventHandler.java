package com.catworldspace.plugin.tom.event;

import com.catworldspace.plugin.tom.common.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEventHandler implements Listener {

    // TODO: 读取自 config.yml 的值
    private final String namePrefix = ".";
    private final String uuidPrefix = "00000000-0000-0000-";

    private final boolean IsDebug = true;

    @EventHandler
    public void onPostLogin(PostLoginEvent event)
    {
        var player = event.getPlayer();

        var uuid = player.getUniqueId();
        var name = player.getName();

        // TODO：动态配置
        var prefixIndex = name.indexOf(namePrefix);
        if (prefixIndex != -1) {
            if (uuid.toString().startsWith(uuidPrefix)){
                var server = CommandHelper.GetServerByName(name.substring(prefixIndex));
                if(IsDebug) {
                    player.sendMessage(CommandHelper.CreateMessage(""));
                }
                if (server!= null) {
                    if (IsDebug) {

                    }
                }
                player.connect();
            }


        }

        //TODO: 将文本移至本地化
        player.disconnect(new ComponentBuilder(ChatColor.RED + "You are not on the server's whitelist!").create());
    }


}

