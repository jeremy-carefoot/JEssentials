package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.bluecreeper111.jessentials.api.api;

public class Clear implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String clearMessage = api.getLangString("clearInvMessage");
		Logger logger = Bukkit.getLogger();
		
		if (args.length < 1) {
			if (!(sender instanceof Player)) {
				api.notPlayer();
				return true;
			} else {
				Player player = (Player) sender;
				if(!player.hasPermission(api.perp() + ".clear")) {
					api.noPermission(player);
					return true;
				} else {
					player.getInventory().clear();
					player.getInventory().setArmorContents(null);
					player.sendMessage(clearMessage.replaceAll("%player%", player.getName().toString()));
					return true;
				}
			}
			
		} else {
			if (!(sender instanceof Player)) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				}
				target.getInventory().clear();
				target.getInventory().setArmorContents(null);
				target.sendMessage(clearMessage.replaceAll("%target%", target.getName().toString()));
				logger.info(api.getLangString("clearInvMessageSender").replaceAll("%player%", target.getName()));
				return true;
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".clear.others")) {
					api.noPermission(player);
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(player, args[0]);
						return true;
					}
					target.getInventory().clear();
					target.getInventory().setArmorContents(null);
					target.sendMessage(clearMessage.replaceAll("%target%", target.getName().toString()));
					player.sendMessage(api.getLangString("clearInvMessageSender").replaceAll("%player%", target.getName()));
					return true;
				}
				
			}
		}
	}

}
