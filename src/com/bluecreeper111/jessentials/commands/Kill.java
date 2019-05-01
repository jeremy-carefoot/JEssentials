package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Kill implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String killMessage = api.getLangString("killMessageSender");
		String suicideMessage = api.getLangString("suicideMessage");
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/kill <player>");
				return true;
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					target.setHealth(0);
					logger.info(killMessage.replaceAll("%player%", target.getDisplayName().toString()));
					target.sendMessage(api.getLangString("killMessage"));
					return true;
				}
			}
		} else {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("kill")) {
				if (!p.hasPermission(api.perp() + ".kill")) {
					api.noPermission(p);
					return true;
				} else {
					if (args.length == 0) {
						p.setHealth(0);
						p.sendMessage(api.getLangString("killMessage"));
						return true;
					} else if (args.length == 1) {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (!p.hasPermission(api.perp() + ".kill.others")) {
							api.noPermission(p);
							return true;
						} else {
							if (target == null) {
								api.pNotFound(p, args[0]);
								return true;
							} else {
								target.setHealth(0);
								p.sendMessage(api.getLangString("killMessageSender").replaceAll("%player%", target.getDisplayName()));
								target.sendMessage(api.getLangString("killMessage"));
								return true;
							}
						}
					} else {
						api.incorrectSyntax(p, "/kill <player>");
						return true;
					}
				}
			} else if (label.equalsIgnoreCase("suicide")) {
				if (!p.hasPermission(api.perp() + ".suicide")) {
					api.noPermission(p);
					return true;
				} else {
					p.setHealth(0);
					Bukkit.broadcastMessage(suicideMessage.replaceAll("%player%", p.getDisplayName().toString()));
					return true;
				}
			} else {
				return true;
			}
		}
	}

}
