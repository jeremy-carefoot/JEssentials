package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class Kick implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/kick <player> [message]");
				return true;
			} else if (args.length > 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (target.hasPermission(api.perp() + ".kick.exempt")) {
						Logger logger = Bukkit.getLogger();
						logger.info(api.getLangString("cannotKick"));
						return true;
					}

					String reason = "";
					for (int i = 1; i < args.length; i++) {
						reason = reason + args[i] + " ";
					}
					Logger logger = Bukkit.getLogger();
					target.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
					logger.info(api.getLangString("kickMessage"));
					return true;
				}

			} else {
				api.incorrectSyntaxConsole("/kick <player> [message]");
				return true;
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".kick")) {
				api.noPermission(p);
				return true;
			} else {

			}
			if (args.length == 0) {
				api.incorrectSyntax(p, "/kick <player> [message]");
				return true;
			} else if (args.length > 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFound(p, args[0]);
					return true;
				} else {
					if (target.hasPermission(api.perp() + ".kick.exempt") || args[0].equals(p.getName())) {
						p.sendMessage(api.getLangString("cannotKick"));
						return true;
					}
					String reason = "";
					for (int i = 1; i < args.length; i++ ) {
						reason = reason + args[i] + " ";
					}
					target.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
					p.sendMessage(api.getLangString("kickMessage"));
					return true;
				}
			} else {
				api.incorrectSyntax(p, "/kick <player> [message]");
				return true;
			}
		}
	}

}
