package com.catworldspace.plugin.tom.commands;

import com.catworldspace.plugin.tom.common.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class TomCommand extends Command {

    public TomCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var message = CommandHelper.CreateMessage(ChatColor.RED + "未知错误，请联系管理员!");



        var text = new TextComponent("Hi " + sender.getName());

        sender.sendMessage(text);
    }
}
