package com.catworldspace.plugin.system.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command

class HubCommand(name: String?, permission: String?, vararg aliases: String?) :
    Command(name, permission, *aliases) {
    override fun execute(sender: CommandSender, args: Array<String>) {

    }
}