package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class Realname implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/realname <nickname>");
				return true;
			} else {
				Player realnamePlayer = Nick.realname.get(args[0]);
				if (realnamePlayer == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					logger.info(api.getLangString("realnameMessage").replaceAll("%player%", realnamePlayer.getDisplayName()).replaceAll("%realname%", realnamePlayer.getName()));
					return true;
				}
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".realname")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					api.incorrectSyntax(p, "/realname <nickname>");
					return true;
				} else {
					Player realnamePlayer = Nick.realname.get(args[0]);
                    if (realnamePlayer == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						p.sendMessage(api.getLangString("realnameMessage").replaceAll("%player%", realnamePlayer.getDisplayName()).replaceAll("%realname%", realnamePlayer.getName()));
					    return true;
					}
				}
			}
		}
	}

}
