package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.bluecreeper111.jessentials.api.api;

public class Workbench implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player player = (Player) sender;
			if (!player.hasPermission(api.perp() + ".workbench")) {
				api.noPermission(player);
				return true;
			} else {
				Inventory craft = Bukkit.createInventory(player, InventoryType.WORKBENCH);
				player.openInventory(craft);
				player.openWorkbench(null, true);
				player.sendMessage(api.getLangString("workbenchMessage"));
				return true;
			}
			
		}
		
	}

}
