package com.catworldspace.plugin.tom.commands;


import com.catworldspace.plugin.tom.common.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
public class RedirectCommand extends Command {

    public RedirectCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var message = CommandHelper.CreateMessage(ChatColor.RED + "未知错误，请联系管理员!");

        //TODO: 将文本移至本地化
        if (!sender.hasPermission(this.getPermission())) {
            message = CommandHelper.CreateMessage(ChatColor.RED + "你没有权限执行此命令!");
        } else {
            switch (args.length) {
                case 1: {
                    if (!(sender instanceof ProxiedPlayer)) {
                        message = CommandHelper.CreateMessage(ChatColor.RED + "执行命令的实体必须是玩家!");
                    } else {
                        ProxiedPlayer player = (ProxiedPlayer) sender;

                        var server = CommandHelper.GetServerByName(args[0]);
                        if (server == null) {
                            message = CommandHelper.CreateMessage(ChatColor.RED + "该服务器不存在!");
                        } else {
                            if (CommandHelper.IsPlayerInServer(player, server.getName())) {
                                message = CommandHelper.CreateMessage(ChatColor.YELLOW + "你已经在 " + server.getName() + " 服务器内了!");
                            } else {

                                //player.connect(server);
                            }
                        }
                    }
                    break;
                }
                case 2: {


                    break;
                }
                default: {
                    message = CommandHelper.CreateMessage(ChatColor.YELLOW + "用法: /redirect <玩家> <服务器>");
                    break;
                }
            }
        }

        sender.sendMessage(message);
    }

}
