package com.catworldspace.plugin.system.commands

import com.catworldspace.plugin.system.common.CommandHelper
import com.catworldspace.plugin.system.common.Variables
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command

class SystemCommand(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        //val message = createMessage(ChatColor.RED.toString() + "未知错误，请联系管理员!")
        val text = CommandHelper.createPrefixedMessage("${Strings.SystemCommand.Greeting} ${sender.name}")
        sender.sendMessage(*text)
    }
}