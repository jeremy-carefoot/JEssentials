package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.bluecreeper111.jessentials.api.api;

public class Invsee implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String invSeeMessage = api.getLangString("invSeeMessage");
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".invsee")) {
				api.noPermission(p);
				return true;
			}
			if (args.length == 0) {
				api.incorrectSyntax(p, "/invsee <player>");
				return true;
			} else {
				if (args[0].equals(p.getName())) {
					p.sendMessage(api.getLangString("invSeeOwn"));
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						Inventory t = target.getInventory();
						p.openInventory(t);
						p.sendMessage(api.replacePlayer(invSeeMessage, p));
						return true;
					}
				}
			}
		}
	}
	

}
