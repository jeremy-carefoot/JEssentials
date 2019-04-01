package com.bluecreeper111.jessentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.api.api;

public class Hat implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String helmMessage = api.getLangString("hatMessage");
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".hat")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					ItemStack item = p.getInventory().getItemInMainHand();
					ItemStack helmet = p.getInventory().getHelmet();
					if (item.getType() == Material.AIR || item == null) {
						p.sendMessage(api.getLangString("mustHoldItem"));
						return true;
					}
					p.getInventory().setHelmet(item);p.getInventory().setItemInMainHand(helmet);
					p.sendMessage(helmMessage.replaceAll("%player%", p.getName().toString()));
					return true;
				} else {
					api.incorrectSyntax(p, "/hat");
					return true;
				}
			}
		}
	}

}
