package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Tp implements CommandExecutor {
	
	private Main plugin;
	
	public Tp(Main pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length != 2) {
				api.incorrectSyntaxConsole("/tp <player 1> <player 2>");
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
				if (Tptoggle.tpToggled.contains(target) || Tptoggle.tpToggled.contains(to)) {
					logger.info(api.getLangString("tpOff") + "(To bypass, use /tpo)");
					return true;
				}
				target.teleport(to);
				target.sendMessage(api.teleportMessage.replaceAll("%delay%", "0"));
				logger.info(api.getLangString("playersTeleported"));
				return true;
				
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".tp")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						if (Tptoggle.tpToggled.contains(target)) {
							p.sendMessage(api.getLangString("tpOff"));
							return true;
						}
						api.tpDelayEntity(target, p, plugin);
						target.sendMessage(api.getLangString("teleportingReceive").replaceAll("%player%", target.getName()));
						return true;
					}
				} else if (args.length == 2) {
					if (!p.hasPermission(api.perp() + ".tp.others")) {
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
						if (Tptoggle.tpToggled.contains(target) || Tptoggle.tpToggled.contains(to)) {
							p.sendMessage(api.getLangString("tpOff"));
							return true;
						}
						api.tpDelayEntity(to, target, plugin);
						to.sendMessage(api.getLangString("teleportingReceive").replaceAll("%player%", target.getName()));
						target.sendMessage(api.getLangString("teleportingSent").replaceAll("%player%", to.getName()));
						p.sendMessage(api.getLangString("playersTeleporting"));
						return true;
						
					}
				} else {
					api.incorrectSyntax(p, "/tp <player>");
					return true;
				}
			}
		}
		return true;
	}

}
