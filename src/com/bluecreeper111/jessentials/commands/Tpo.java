package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.event.playerDeath;

public class Tpo implements CommandExecutor {

	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length != 2) {
				api.incorrectSyntaxConsole("/tpo <player 1> <player 2>");
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				Player to = Bukkit.getPlayerExact(args[1]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				}
				if (to == null) {
					api.pNotFoundConsole(args[1]);
					return true;
				}
				playerDeath.deathInfo.put(target.getName(), target.getLocation());
				target.teleport(to);
				target.sendMessage(api.getLangString("teleportMessage").replaceAll("%delay%", "0"));
				logger.info(api.getLangString("playersTeleported"));
				return true;

			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".tpo")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						playerDeath.deathInfo.put(p.getName(), p.getLocation());
						p.teleport(target);
						p.sendMessage(api.teleportMessage.replaceAll("%delay%", "0"));
						if (!Vanish.vanishedPlayers.contains(p) || !(p.getGameMode() == GameMode.SPECTATOR)) {
						target.sendMessage(api.getLangString("teleportingReceive").replaceAll("%player%", p.getName()));
						}
						return true;
					}
				} else if (args.length == 2) {
					if (!p.hasPermission(api.perp() + ".tpo.others")) {
						api.noPermission(p);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						Player to = Bukkit.getPlayerExact(args[1]);
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						}
						if (to == null) {
							api.pNotFound(p, args[1]);
							return true;
						}
						playerDeath.deathInfo.put(target.getName(), target.getLocation());
						target.teleport(to);
						to.sendMessage(api.getLangString("teleportingReceive").replaceAll("%player%", target.getName()));
						target.sendMessage(api.getLangString("teleportingSent").replaceAll("%player%", to.getName()));
						p.sendMessage(api.getLangString("playersTeleported"));
						return true;

					}
				} else {
					api.incorrectSyntax(p, "/tpo <player>");
					return true;
				}
			}
		}
		return true;
	}

}
