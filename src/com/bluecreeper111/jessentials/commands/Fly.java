package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Fly implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length < 1) {
			if (!(sender instanceof Player)) {
				api.notPlayer();
				return true;
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".fly")) {
					api.noPermission(player);
					return true;
				} else {
					if (player.getAllowFlight() == true) {
						player.setAllowFlight(false);
						player.sendMessage(api.getLangString("flyDisabled"));
						return true;
					} else {
						player.setAllowFlight(true);
						player.sendMessage(api.getLangString("flyEnabled"));
						return true;
					}
				}
			}
		} else {
			if (!(sender instanceof Player)) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (target.getAllowFlight() == true) {
						target.setAllowFlight(false);
						target.sendMessage(api.getLangString("flyDisabled"));
						sender.sendMessage(api.getLangString("flyMessageSender").replaceAll("%player%", target.getName()));
						return true;
					} else {
						target.setAllowFlight(true);
						target.sendMessage(api.getLangString("flyEnabled"));
						sender.sendMessage(api.getLangString("flyMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				}
			} else {
				Player player = (Player) sender;
				if (!(player.hasPermission(api.perp() + ".fly.others"))) {
					api.noPermission(player);
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(player, args[0]);
						return true;
					} else {
						if (target.getAllowFlight() == false) {
							target.setAllowFlight(true);
							target.sendMessage(api.getLangString("flyEnabled"));
							player.sendMessage(api.getLangString("flyMessageSender").replaceAll("%player%", target.getName()));
							return true;
						} else {
							target.setAllowFlight(false);
							target.sendMessage(api.getLangString("flyDisabled"));
							player.sendMessage(api.getLangString("flyMessageSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}
	}

}
