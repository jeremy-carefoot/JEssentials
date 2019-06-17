package com.bluecreeper111.jessentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.api.api;

public class Item implements CommandExecutor {
	
	public boolean ifInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String itemMessage = api.getLangString("itemMessage");
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".item")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length != 2) {
					api.incorrectSyntax(p, "/item <item id> <amount>");
					return true;
				}
				if (Material.getMaterial(args[0].toUpperCase()) == null) {
					Class<? extends Material> cls = Material.class;
					try {
						Field f = cls.getField(args[0].toUpperCase());
						Object obj = f.get(cls);
						if (cls.isAssignableFrom(obj.getClass())) {
							Material value = cls.cast(obj);
							if (this.ifInt(args[1])) {
								int amount = Integer.parseInt(args[1]);
								ItemStack item = new ItemStack(value, amount);
								p.getInventory().addItem(item);
								p.sendMessage(itemMessage.replaceAll("%player%", p.getName().toString()).replaceAll("%item%", args[0]));
								return true;
							} else {
								api.incorrectSyntax(p, "/item <item id> <amount>");
								return true;
							}
						}
					} catch (NoSuchFieldException e) {
						p.sendMessage(api.getLangString("itemNotFound"));
					} catch (Exception e) {
						p.sendMessage(api.getLangString("itemNotFound"));
					}
					return true;
				} else {
					if (this.ifInt(args[1])) {
						int amount = Integer.parseInt(args[1]);
						ItemStack item = new ItemStack(Material.getMaterial(args[0].toUpperCase()), amount);
						p.getInventory().addItem(item);
						p.sendMessage(itemMessage.replaceAll("%player%", p.getName().toString()).replaceAll("%item%", args[0]));
						return true;
					
					} else {
						api.incorrectSyntax(p, "/item <item id> <amount>");
						return true;
					}
				}
			}
		}
		
	}

}
