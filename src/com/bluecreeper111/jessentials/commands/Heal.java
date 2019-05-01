package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Heal implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Logger logger = Bukkit.getLogger();

		if (args.length < 1) {
			if (!(sender instanceof Player)) {
				api.notPlayer();
				return true;
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".heal")) {
					api.noPermission(player);
					return true;
				} else if (player.hasPermission(api.perp() + ".heal")) {
					player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					player.setFoodLevel(20);
					player.sendMessage(api.getLangString("healMessage"));
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
					target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					target.setFoodLevel(20);
					target.sendMessage(api.getLangString("healMessage"));
					logger.info(api.getLangString("healMessageSender").replaceAll("%player%", target.getDisplayName()));
					return true;
				}
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".heal.others")) {
					api.noPermission(player);
					return true;
				} else if (player.hasPermission(api.perp() + ".heal.others")) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(player, args[0]);
						return true;
					} else {
						target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						target.setFoodLevel(20);
						target.sendMessage(api.getLangString("healMessage"));
						sender.sendMessage(api.getLangString("healMessageSender").replaceAll("%player%", target.getDisplayName()));
						return true;
					}
				}

			}

			return true;
		}
		return true;
	}
}
