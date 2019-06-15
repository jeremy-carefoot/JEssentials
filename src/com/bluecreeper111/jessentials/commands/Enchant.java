package com.bluecreeper111.jessentials.commands;

import java.lang.reflect.Field;

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
		Integer t = 0;
		for (Enchantment value : Enchantment.values()) {
			if (value.getKey().getKey().equalsIgnoreCase(enchString)) {
				t = 1;
				return value;
			}
		}
		if (t == 0) {
			if(enchString.equalsIgnoreCase("aqua_affinity")) { enchString = "WATER_WORKER"; }
			else if(enchString.equalsIgnoreCase("bane_of_arthropods")) { enchString = "DAMAGE_ARTHROPODS"; }
			else if(enchString.equalsIgnoreCase("blast_protection")) { enchString = "PROTECTION_EXPLOSIONS"; }
			else if(enchString.equalsIgnoreCase("curse_of_binding")) { enchString = "BINDING_CURSE"; }
			else if(enchString.equalsIgnoreCase("curse_of_vanishing")) { enchString = "VANISHING_CURSE"; }
			else if(enchString.equalsIgnoreCase("feather_falling")) { enchString = "PROTECTION_FALL"; }
			else if(enchString.equalsIgnoreCase("fire_protection")) { enchString = "PROTECTION_FIRE"; }
			else if(enchString.equalsIgnoreCase("flame")) { enchString = "ARROW_FIRE"; }
			else if(enchString.equalsIgnoreCase("fortune")) { enchString = "LOOT_BONUS_BLOCKS"; }
			else if(enchString.equalsIgnoreCase("infinity")) { enchString = "ARROW_INFINITE"; }
			else if(enchString.equalsIgnoreCase("luck_of_the_sea")) { enchString = "LUCK"; }
			else if(enchString.equalsIgnoreCase("power")) { enchString = "ARROW_DAMAGE"; }
			else if(enchString.equalsIgnoreCase("projectile_protection")) { enchString = "PROTECTION_PROJECTILE"; }
			else if(enchString.equalsIgnoreCase("protection")) { enchString = "PROTECTION_ENVIRONMENTAL"; }
			else if(enchString.equalsIgnoreCase("punch")) { enchString = "ARROW_KNOCKBACK"; }
			else if(enchString.equalsIgnoreCase("respiration")) { enchString = "OXYGEN"; }
			else if(enchString.equalsIgnoreCase("sharpness")) { enchString = "DAMAGE_ALL"; }
			else if(enchString.equalsIgnoreCase("smite")) { enchString = "DAMAGE_UNDEAD"; }
			else if(enchString.equalsIgnoreCase("unbreaking")) { enchString = "DURABILITY"; }
			
			Class<? extends Enchantment> cls = Enchantment.class;
			try {
			    Field f = cls.getField(enchString.toUpperCase());
			    Object obj = f.get(cls);
			    if (cls.isAssignableFrom(obj.getClass())) {
			        Enchantment value = cls.cast(obj);
			        return value;
			    }
			} catch (NoSuchFieldException e) {
				return null;
			} catch (Exception e) {
				//e.printStackTrace();
				return null;
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
