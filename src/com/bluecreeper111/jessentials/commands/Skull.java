package com.bluecreeper111.jessentials.commands;

import org.bukkit.Material;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.bluecreeper111.jessentials.api.api;



public class Skull implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String skullMessage = api.getLangString("skullMessage");
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".skull")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length != 1) {
					api.incorrectSyntax(p, "/skull <player>");
					return true;
				} else {
					ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
					SkullMeta  m = (SkullMeta)item.getItemMeta();
					m.setOwner(args[0]);
					item.setItemMeta(m);
					p.getInventory().addItem(new ItemStack[] { item });
					p.sendMessage(skullMessage.replaceAll("%player%", args[0]).replaceAll("%skullPlayer%", args[0]));
					return true;
				}
			}
		}
	}

}
