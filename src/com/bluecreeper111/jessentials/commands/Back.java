package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.event.playerDeath;

public class Back implements CommandExecutor {

	private Main plugin;

	public Back(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/back <player>");
				return true;
			} else if (args.length > 0) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (playerDeath.deathInfo.containsKey(target.getName())) {
						Location loc = playerDeath.deathInfo.get(target.getName());
						api.tpDelayLoc(loc, target, plugin);
						logger.info(api.getLangString("backSender").replaceAll("%player%", target.getName()));
						return true;
					} else {
						logger.info(api.getLangString("backNotDiedSender"));
						return true;
					}
				}

			} else {
				api.incorrectSyntaxConsole("/back <player>");
				return true;
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission(api.perp() + ".back")) {
					if (playerDeath.deathInfo.containsKey(p.getName())) {
						Location loc = playerDeath.deathInfo.get(p.getName());
						api.tpDelayLoc(loc, p, plugin);
						return true;
					} else {
						p.sendMessage(api.getLangString("backNotDied"));
						return true;
					}
					
				} else {
					api.noPermission(p);
					return true;
				}
			} else if (args.length == 1) {
				if (p.hasPermission(api.perp() + ".back.others")) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						if (playerDeath.deathInfo.containsKey(target.getName())) {
							Location loc = playerDeath.deathInfo.get(target.getName());
							api.tpDelayLoc(loc, target, plugin);
							p.sendMessage(api.getLangString("backSender").replaceAll("%player%", target.getName()));
							return true;
						} else {
							p.sendMessage(api.getLangString("backNotDiedSender"));
							return true;
						}
					}
				} else {
					api.noPermission(p);
					return true;
				}
			} else {
				api.incorrectSyntax(p, "/back ; /back <player>");
				return true;
			}
		}
	}

}
