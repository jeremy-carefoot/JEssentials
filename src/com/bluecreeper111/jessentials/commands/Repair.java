package com.bluecreeper111.jessentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.bluecreeper111.jessentials.api.api;

public class Repair implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String repairMessage = api.getLangString("repairMessage");
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".repair")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if (item == null || item.getType() == Material.AIR) {
						p.sendMessage(api.getLangString("mustHoldItem"));
						return true; 
					}
					Damageable im = (Damageable) item.getItemMeta();
					im.setDamage(0);
					item.setItemMeta((ItemMeta) im);
					p.sendMessage(repairMessage.replaceAll("%player%", p.getName().toString()));
					return true;
				} else if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
					if (!p.hasPermission(api.perp() + ".repair.all")) {
						api.noPermission(p);
						return true;
					} else {
						for (int i = 0; i < p.getInventory().getSize(); i++) {
							if (!(p.getInventory().getItem(i) == null) && p.getInventory().getItem(i).getType() != Material.AIR) {
								p.getInventory().getItem(i).setDurability((short) 0);
								if (i == p.getInventory().getSize())
									break;
							}
						}
						
						if (!(p.getInventory().getBoots() == null)) { p.getInventory().getBoots().setDurability((short)0); }
						if (!(p.getInventory().getHelmet() == null)) { p.getInventory().getHelmet().setDurability((short)0); }
						if (!(p.getInventory().getChestplate() == null)) { p.getInventory().getChestplate().setDurability((short)0); }
						if (!(p.getInventory().getLeggings() == null)) { p.getInventory().getLeggings().setDurability((short)0); }
						p.sendMessage(api.getLangString("repairAll"));
						return true;
					}
				} else {
					api.incorrectSyntax(p, "/repair [all]");
					return true;
				}
			}
		}
	}

}
