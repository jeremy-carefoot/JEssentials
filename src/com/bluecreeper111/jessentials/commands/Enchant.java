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

	int min = 0;
	int max = 0;

	public boolean ifInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;

	}

	private Enchantment findEnchantmentByName(String name) {
		if (name.equalsIgnoreCase("protection") || name.equalsIgnoreCase("protection_environmental")) {
			max = Enchantment.PROTECTION_ENVIRONMENTAL.getMaxLevel();
			min = Enchantment.PROTECTION_ENVIRONMENTAL.getStartLevel();
			return Enchantment.PROTECTION_ENVIRONMENTAL;
		}
		if (name.equalsIgnoreCase("fire_protection")) {
			max = Enchantment.PROTECTION_FIRE.getMaxLevel();
			min = Enchantment.PROTECTION_FIRE.getStartLevel();
			return Enchantment.PROTECTION_FIRE;
		}
		if (name.equalsIgnoreCase("fall_protection")) {
			max = Enchantment.PROTECTION_FALL.getMaxLevel();
			min = Enchantment.PROTECTION_FALL.getStartLevel();
			return Enchantment.PROTECTION_FALL;
		}
		if (name.equalsIgnoreCase("explosions_protection")
				|| name.equalsIgnoreCase("blast_protection")) {
			min = Enchantment.PROTECTION_EXPLOSIONS.getStartLevel();
			max = Enchantment.PROTECTION_EXPLOSIONS.getMaxLevel();
			return Enchantment.PROTECTION_EXPLOSIONS;
		}
		if (name.equalsIgnoreCase("projectile_protection")) {
			min = Enchantment.PROTECTION_PROJECTILE.getStartLevel();
			max = Enchantment.PROTECTION_PROJECTILE.getMaxLevel();
			return Enchantment.PROTECTION_PROJECTILE;
		}
		if (name.equalsIgnoreCase("respiration")) {
			min = Enchantment.OXYGEN.getStartLevel();
			max = Enchantment.OXYGEN.getMaxLevel();
			return Enchantment.OXYGEN;
		}
		if (name.equalsIgnoreCase("aqua_affinity")) {
			min = Enchantment.WATER_WORKER.getStartLevel();
			max = Enchantment.WATER_WORKER.getMaxLevel();
			return Enchantment.WATER_WORKER;
		}
		if (name.equalsIgnoreCase("thorns")) {
			min = Enchantment.THORNS.getStartLevel();
			max = Enchantment.THORNS.getMaxLevel();
			return Enchantment.THORNS;
		}
		if (name.equalsIgnoreCase("depth_strider")) {
			min = Enchantment.DEPTH_STRIDER.getStartLevel();
			max = Enchantment.DEPTH_STRIDER.getMaxLevel();
			return Enchantment.DEPTH_STRIDER;
		}
		if (name.equalsIgnoreCase("frost_walker")) {
			min = Enchantment.FROST_WALKER.getStartLevel();
			max = Enchantment.FROST_WALKER.getMaxLevel();
			return Enchantment.FROST_WALKER;
		}
		if (name.equalsIgnoreCase("binding_curse")) {
			min = Enchantment.BINDING_CURSE.getStartLevel();
			max = Enchantment.BINDING_CURSE.getMaxLevel();
			return Enchantment.BINDING_CURSE;
		}
		if (name.equalsIgnoreCase("sharpness")) {
			min = Enchantment.DAMAGE_ALL.getStartLevel();
			max = Enchantment.DAMAGE_ALL.getMaxLevel();
			return Enchantment.DAMAGE_ALL;
		}
		if (name.equalsIgnoreCase("smite")) {
			min = Enchantment.DAMAGE_UNDEAD.getStartLevel();
			max = Enchantment.DAMAGE_UNDEAD.getMaxLevel();
			return Enchantment.DAMAGE_UNDEAD;
		}
		if (name.equalsIgnoreCase("bane_of_arthropods")) {
			min = Enchantment.DAMAGE_ARTHROPODS.getStartLevel();
			max = Enchantment.DAMAGE_ARTHROPODS.getMaxLevel();
			return Enchantment.DAMAGE_ARTHROPODS;
		}
		if (name.equalsIgnoreCase("knockback")) {
			min = Enchantment.KNOCKBACK.getStartLevel();
			max = Enchantment.KNOCKBACK.getMaxLevel();
			return Enchantment.KNOCKBACK;
		}
		if (name.equalsIgnoreCase("fire_aspect")) {
			min = Enchantment.FIRE_ASPECT.getStartLevel();
			max = Enchantment.FIRE_ASPECT.getMaxLevel();
			return Enchantment.FIRE_ASPECT;
		}
		if (name.equalsIgnoreCase("looting")) {
			min = Enchantment.LOOT_BONUS_MOBS.getStartLevel();
			max = Enchantment.LOOT_BONUS_MOBS.getMaxLevel();
			return Enchantment.LOOT_BONUS_MOBS;
		}
		if (name.equalsIgnoreCase("sweeping_edge")) {
			min = Enchantment.SWEEPING_EDGE.getStartLevel();
			max = Enchantment.SWEEPING_EDGE.getMaxLevel();
			return Enchantment.SWEEPING_EDGE;
		}
		if (name.equalsIgnoreCase("efficiency")) {
			min = Enchantment.DIG_SPEED.getStartLevel();
			max = Enchantment.DIG_SPEED.getMaxLevel();
			return Enchantment.DIG_SPEED;
		}
		if (name.equalsIgnoreCase("silk_touch")) {
			min = Enchantment.SILK_TOUCH.getStartLevel();
			max = Enchantment.SILK_TOUCH.getMaxLevel();
			return Enchantment.SILK_TOUCH;
		}
		if (name.equalsIgnoreCase("unbreaking")) {
			min = Enchantment.DURABILITY.getStartLevel();
			max = Enchantment.DURABILITY.getMaxLevel();
			return Enchantment.DURABILITY;
		}
		if (name.equalsIgnoreCase("fortune")) {
			min = Enchantment.LOOT_BONUS_BLOCKS.getStartLevel();
			max = Enchantment.LOOT_BONUS_BLOCKS.getMaxLevel();
			return Enchantment.LOOT_BONUS_BLOCKS;
		}
		if (name.equalsIgnoreCase("power")) {
			min = Enchantment.ARROW_DAMAGE.getStartLevel();
			max = Enchantment.ARROW_DAMAGE.getMaxLevel();
			return Enchantment.ARROW_DAMAGE;
		}
		if (name.equalsIgnoreCase("punch")) {
			min = Enchantment.ARROW_KNOCKBACK.getStartLevel();
			max = Enchantment.ARROW_KNOCKBACK.getMaxLevel();
			return Enchantment.ARROW_KNOCKBACK;
		}
		if (name.equalsIgnoreCase("flame")) {
			min = Enchantment.ARROW_FIRE.getStartLevel();
			max = Enchantment.ARROW_FIRE.getMaxLevel();
			return Enchantment.ARROW_FIRE;
		}
		if (name.equalsIgnoreCase("infinity")) {
			min = Enchantment.ARROW_INFINITE.getStartLevel();
			max = Enchantment.ARROW_INFINITE.getMaxLevel();
			return Enchantment.ARROW_INFINITE;
		}
		if (name.equalsIgnoreCase("luck_of_the_sea")) {
			min = Enchantment.LUCK.getStartLevel();
			max = Enchantment.LUCK.getMaxLevel();
			return Enchantment.LUCK;
		}
		if (name.equalsIgnoreCase("lure")) {
			min = Enchantment.LURE.getStartLevel();
			max = Enchantment.LURE.getMaxLevel();
			return Enchantment.LURE;
		}
		if (name.equalsIgnoreCase("mending")) {
			min = Enchantment.MENDING.getStartLevel();
			max = Enchantment.MENDING.getMaxLevel();
			return Enchantment.MENDING;
		}
		if (name.equalsIgnoreCase("vanishing_curse")) {
			min = Enchantment.VANISHING_CURSE.getStartLevel();
			max = Enchantment.VANISHING_CURSE.getMaxLevel();
			return Enchantment.VANISHING_CURSE;
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
				Enchantment enchant = this.findEnchantmentByName(args[0]);
				ItemStack item = player.getInventory().getItemInMainHand();
				if (item == null || item.getType() == Material.AIR) {
					player.sendMessage(api.getLangString("mustHoldItem"));
					return true;
				} else {
					if (!(this.ifInt(args[1]))) {
						api.incorrectSyntax(player, "/enchant <enchantment> <level>");
						return true;
					} else {
						if (this.findEnchantmentByName(args[0]) == null) {
							player.sendMessage(api.getLangString("enchantNotFound"));
							return true;
						} else {
							if (player.hasPermission(api.perp() + ".enchant." + args[0].toLowerCase())
									|| player.hasPermission(api.perp() + ".enchant.*")) {
								int level = Integer.parseInt(args[1]);
								this.findEnchantmentByName(args[0]);
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
