package com.catworldspace.plugin.system.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command

class SystemCommand(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        //val message = createMessage(ChatColor.RED.toString() + "未知错误，请联系管理员!")
        val text = TextComponent("Hi " + sender.name)
        sender.sendMessage(text)
    }
}