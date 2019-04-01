package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class Feed implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Logger logger = Bukkit.getLogger();
		
		if (args.length < 1) {
			if (!(sender instanceof Player)) {
				api.notPlayer();
				return true;
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".feed")) {
					api.noPermission(player);
					return true;
				} else if (player.hasPermission(api.perp() + ".feed")) {
					player.setFoodLevel(20);
					player.sendMessage(api.getLangString("feedMessage"));
					return true;
				}
			}
		} else {
			if (!(sender instanceof Player)) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					target.setFoodLevel(20);
					target.sendMessage(api.getLangString("feedMessage"));
					logger.info(api.getLangString("feedMessageSender").replaceAll("%player%", target.getName()));
					return true;
				}
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".feed.others")) {
					api.noPermission(player);
					return true;
				} else if (player.hasPermission(api.perp() + ".feed.others")) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(player, args[0]);
						return true;
					} else {
						target.setFoodLevel(20);
						target.sendMessage(api.getLangString("feedMessage"));
						sender.sendMessage(api.getLangString("feedMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				}

			}

			return true;
		}
		return true;
	}

}
