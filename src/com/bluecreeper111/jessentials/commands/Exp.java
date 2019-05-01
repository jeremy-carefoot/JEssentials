package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Exp implements CommandExecutor {

	public boolean isInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String expMessage = api.getLangString("expMessage");
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length != 2) {
				api.incorrectSyntaxConsole("/exp <player> <amount in levels>");
				return true;
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else if (this.isInt(args[1])) {
					int value = Integer.parseInt(args[1]);
					target.giveExpLevels(value);
					target.sendMessage(expMessage.replaceAll("%player%", target.getName().toString())
							.replaceAll("%exp%", (Integer.toString(value) + " levels")));
					logger.info(api.getLangString("expMessageSender").replaceAll("%player%", target.getName()).replaceAll("%exp%", args[1]));
					return true;
				} else {
					api.incorrectSyntaxConsole("/exp <player> <amount in levels>");
					return true;
				}
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".exp")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 1) {
					if (this.isInt(args[0])) {
						int value = Integer.parseInt(args[0]);
						p.giveExpLevels(value);
						p.sendMessage(expMessage.replaceAll("%player%", p.getName().toString()).replaceAll("%exp%",
								(Integer.toString(value) + " levels")));
						return true;
					} else {
						api.incorrectSyntax(p, "/exp <amount in levels>");
						return true;
					}
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (!p.hasPermission(api.perp() + ".exp.others")) {
						api.noPermission(p);
						return true;
					} else {
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} else {
							if (this.isInt(args[1])) {
								int value = Integer.parseInt(args[1]);
								target.giveExpLevels(value);
								target.sendMessage(expMessage.replaceAll("%player%", target.getName().toString())
							.replaceAll("%exp%", (Integer.toString(value) + " levels")));
								p.sendMessage(api.getLangString("expMessageSender").replaceAll("%player%", target.getName()).replaceAll("%exp%", args[1]));
								
								return true;
							} else {
								api.incorrectSyntax(p, "/exp <player> <amount in levels>");
								return true;
							}

						}
					}
				} else {
					api.incorrectSyntax(p, "/exp <amount in levels> || /exp <player> <amount in levels>");
					return true;
				}

			}
		}
	}

}
