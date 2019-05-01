package com.bluecreeper111.jessentials.commands;


import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.api.api;

public class Enchant implements CommandExecutor {
	
	private Enchantment getEnchantment(String enchString) {
       for (Enchantment value : Enchantment.values()) {
    	   if (value.getKey().getKey().equalsIgnoreCase(enchString)) {
    		   return value;
    	   }
       }
       return null;
    }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player player = (Player) sender;
			if (args.length != 2) {
				api.incorrectSyntax(player, "/enchant <enchantment> <level>");
				return true;
			}
			if (!(player.hasPermission(api.perp() + ".enchant") || player.hasPermission(api.perp() + ".enchant.*"))) {
				api.noPermission(player);
				return true;
			} else {
				Enchantment enchant = this.getEnchantment(args[0]);
				ItemStack item = player.getInventory().getItemInMainHand();
				if (item == null || item.getType() == Material.AIR) {
					player.sendMessage(api.getLangString("mustHoldItem"));
					return true;
				} else {
					if (!(api.isInt(args[1]))) {
						api.incorrectSyntax(player, "/enchant <enchantment> <level>");
						return true;
					} else {
						if (this.getEnchantment(args[0]) == null) {
							player.sendMessage(api.getLangString("enchantNotFound"));
							return true;
						} else {
							if (player.hasPermission(api.perp() + ".enchant." + args[0].toLowerCase())
									|| player.hasPermission(api.perp() + ".enchant.*")) {
								int level = Integer.parseInt(args[1]);
								this.getEnchantment(args[0]);
								int max = enchant.getMaxLevel();
								int min = enchant.getStartLevel();
								if (max < level || min > level) {
									player.sendMessage(api.getLangString("enchantOutOfBounds").replaceAll("%max%", Integer.toString(max)));
									return true;
								} else {
									if (!(enchant.canEnchantItem(item))) {
										player.sendMessage(api.getLangString("enchantmentNotCompatible"));
										return true;
									} else {
										item.addEnchantment(enchant, level);
										player.sendMessage(api.getLangString("enchantMessage"));
										return true;
									}
								}
							} else {
								api.noPermission(player);
								return true;
							}
						}
					}

				}
			}

		}
	}

}
