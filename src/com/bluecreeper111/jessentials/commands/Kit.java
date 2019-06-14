package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.event.cooldownManager;

public class Kit extends JCommand {
	
	public Kit() {
		super("kit", (plugin.getConfig().getString("permissionPrefix") + ".kit"), false);
	}
	
	public static Kit kit;

	public static File kitsFile = new File("plugins/JEssentials", "kits.yml");
	public static YamlConfiguration kits = YamlConfiguration.loadConfiguration(kitsFile);
	private cooldownManager cooldownManager = new cooldownManager();
	
	public void save(Player p, String kit) {
		try {
			kits.load(kitsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 36; i++) {
			ItemStack add = p.getInventory().getItem(i);
			if (add == null || add.getType() == Material.AIR) continue;
			kits.set("Kit." + kit + ".items." + i, add);
		}
		kit = kit.toLowerCase();
	 	kits.set("Kit." + kit + ".armor.helmet", p.getInventory().getHelmet() != null ? p.getInventory().getHelmet() : "air");
	 	kits.set("Kit." + kit + ".armor.chestplate", p.getInventory().getChestplate() != null ? p.getInventory().getChestplate() : "air");
	 	kits.set("Kit." + kit + ".armor.leggings", p.getInventory().getLeggings() != null ? p.getInventory().getLeggings() : "air");
	 	kits.set("Kit." + kit + ".armor.boots", p.getInventory().getBoots() != null ? p.getInventory().getBoots() : "air");
		try {
			kits.save(kitsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(String kit) {
		try {
			kits.load(kitsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if (kits.isSet("Kit." + kit.toLowerCase())) {
			kits.set("Kit." + kit.toLowerCase(), null);
		}
		try {
			kits.save(kitsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void addInventory(Player p, String kit) {
		try {
			kits.load(kitsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if (kits.isSet("Kit." + kit.toLowerCase())) {
			for (String num : kits.getConfigurationSection("Kit." + kit + ".items").getKeys(false)) {
				api.addItem(p, kits.getItemStack("Kit." + kit + ".items." + num));
			}
			for (String armor : kits.getConfigurationSection("Kit." + kit + ".armor").getKeys(false)) {
				if (kits.get("Kit." + kit + ".armor." + armor).equals("air")) continue;
				ItemStack give = kits.getItemStack("Kit." + kit + ".armor." + armor);
				if (armor.equals("helmet")) {
					if (p.getInventory().getHelmet() == null || p.getInventory().getHelmet().getType() == Material.AIR) {
						p.getInventory().setHelmet(give);
					} else {
						api.addItem(p, give);
					}
				}
				if (armor.equals("chestplate")) {
					if (p.getInventory().getChestplate() == null || p.getInventory().getChestplate().getType() == Material.AIR) {
						p.getInventory().setChestplate(give);
					} else {
						api.addItem(p, give);
					}
				}
				if (armor.equals("leggings")) {
					if (p.getInventory().getLeggings() == null || p.getInventory().getLeggings().getType() == Material.AIR) {
						p.getInventory().setLeggings(give);
					} else {
						api.addItem(p, give);
					}
				}
				if (armor.equals("boots")) {
					if (p.getInventory().getBoots() == null || p.getInventory().getBoots().getType() == Material.AIR) {
						p.getInventory().setBoots(give);
					} else {
						api.addItem(p, give);
					}
				}
			}
		}
	}
	
	public void sendKitList(Player p) {
		if (!kits.getConfigurationSection("Kit").getKeys(true).isEmpty()) {
			String text = "";
			for (String kits : kits.getConfigurationSection("Kit").getKeys(false)) {
				text = text + kits + ", ";
			}
			p.sendMessage("§6Kits: §r" + text);
		} else {
			p.sendMessage(api.getLangString("noKits"));
		}
	}

	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("kits")) {
			sendKitList(p);
			return;
		}
		if (label.equalsIgnoreCase("kit")) {
			if (args.length == 0) {
				sendKitList(p);
				return;
			}
			if (kits.isSet("Kit." + args[0].toLowerCase())) {
				if (!p.hasPermission(api.perp() + ".kit." + args[0].toLowerCase())) {
					if (!p.hasPermission(api.perp() + ".kit.*")) {
						api.noPermission(p);
						return;
					}
				}
				if (kits.isSet("Kit." + args[0].toLowerCase() + ".delay")) {
					int delay = kits.getInt("Kit." + args[0].toLowerCase() + ".delay");
					long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p, args[0].toLowerCase());
					if (p.hasPermission(api.perp() + ".kit.delay.bypass." + args[0].toLowerCase()) || p.hasPermission(api.perp() + ".kit.delay.bypass.*")) {
						cooldownManager.setCooldown(p, System.currentTimeMillis(), args[0].toLowerCase());
						Kit.addInventory(p, args[0]);
						p.sendMessage(api.getLangString("kitMessage").replaceAll("%kit%", args[0]).replaceAll("%player%", p.getName()));
					} else {
						if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= delay) {
							cooldownManager.setCooldown(p, System.currentTimeMillis(), args[0].toLowerCase());
							Kit.addInventory(p, args[0]);
							p.sendMessage(api.getLangString("kitMessage").replaceAll("%kit%", args[0]).replaceAll("%player%", p.getName()));
						} else {
							String replace = Long.toString(Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - delay));
							p.sendMessage(api.getLangString("kitCooldown").replaceAll("%kit%", args[0].toLowerCase()).replaceAll("%timeLeft%", replace));
						}
					}
					return;
				} else {
					Kit.addInventory(p, args[0]);
					p.sendMessage(api.getLangString("kitMessage").replaceAll("%kit%", args[0]).replaceAll("%player%", p.getName()));
				}
				return;
			} else {
				p.sendMessage(api.getLangString("kitNotFound"));
			}
			return;
		}
		if (label.equalsIgnoreCase("kitadd")) {
			if (!p.hasPermission(api.perp() + ".kit.add")) {
				api.noPermission(p);
				return;
			}
			if (args.length != 1) {
				api.incorrectSyntax(p, "/kitadd <name>");
				return;
			}
			this.save(p, args[0]);
			p.sendMessage(api.getLangString("kitCreated").replaceAll("%kit%", args[0]));
			return;
		}
		if (label.equalsIgnoreCase("kitdelete")) {
			if (!p.hasPermission(api.perp() + ".kit.delete")) { 
				api.noPermission(p);
				return;
			}
			if (args.length != 1) {
				api.incorrectSyntax(p, "/kitdelete <name>");
				return;
			}
			if (kits.isSet("Kit." + args[0].toLowerCase())) {
				this.delete(args[0].toLowerCase());
				p.sendMessage(api.getLangString("kitDeleted"));
				return;
			} else {
				p.sendMessage(api.getLangString("kitNotFound"));
				return;
			}
		}
		if (label.equalsIgnoreCase("kitoptions")) {
			if (args.length < 1) {
				api.incorrectSyntax(p, "/kitoptions [firstjoin:delay] <name>");
				return;
			}
			if (args[0].equalsIgnoreCase("firstjoin")) {
				if (!p.hasPermission(api.perp() + ".kit.firstjoin")) {
					api.noPermission(p);
					return;
				}
				if (args.length != 2) {
					api.incorrectSyntax(p, "/kitoptions firstjoin <name>");
					return;
				}
				if (kits.isSet("Kit." + args[1].toLowerCase())) {
					boolean ison = kits.isSet("Kit." + args[1].toLowerCase() + ".firstjoin") ? kits.getBoolean("Kit." + args[1].toLowerCase() + ".firstjoin") : false;
					if (ison) {
						kits.set("Kit." + args[1].toLowerCase() + ".firstjoin", false);
						try {
							kits.save(kitsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(api.getLangString("kitFirstJoin").replaceAll("%kit%", args[1].toLowerCase()).replaceAll("%toggle%", "false"));
						return;
					}
					kits.set("Kit." + args[1].toLowerCase() + ".firstjoin", true);
					try {
						kits.save(kitsFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					p.sendMessage(api.getLangString("kitFirstJoin").replaceAll("%kit%", args[1].toLowerCase()).replaceAll("%toggle%", "true"));
					return;
				} else {
					p.sendMessage(api.getLangString("kitNotFound"));
					return;
				}
			}
			if (args[0].equalsIgnoreCase("delay")) {
				if (!p.hasPermission(api.perp() + ".kit.setdelay")) {
					api.noPermission(p);
					return;
				}
				if (args.length != 3) {
					api.incorrectSyntax(p, "/kitoptions delay <kit> <delay>");
					return;
				}
				if (kits.isSet("Kit." + args[1].toLowerCase())) {
					if (api.isInt(args[2])) {
						int delay = Integer.parseInt(args[2]);
						kits.set("Kit." + args[1].toLowerCase() + ".delay", delay);
						try {
							kits.save(kitsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(api.getLangString("kitDelaySet").replaceAll("%time%", Integer.toString(delay)).replaceAll("%kit%", args[1].toLowerCase()));
						return;
					} else {
						p.sendMessage(api.getLangString("invalidArgumentNumber"));
						return;
					}
				} else {
					p.sendMessage(api.getLangString("kitNotFound"));
					return;
				}
			}
			api.incorrectSyntax(p, "/kitoptions [firstjoin:delay] <name>");
			return;
		}
		if (label.equalsIgnoreCase("kitgive")) {
			if (!p.hasPermission(api.perp() + ".kit.give")) {
				api.noPermission(p);
				return;
			}
			if (args.length != 2) {
				api.incorrectSyntax(p, "/kitgive <player> <kit>");
				return;
			}
			@SuppressWarnings("deprecation")
			Player t = Bukkit.getPlayerExact(args[0]);
			if (t == null) {
				api.pNotFound(p, args[0]);
				return;
			}
			if (kits.isSet("Kit." + args[1].toLowerCase())) {
				Kit.addInventory(t, args[1].toLowerCase());
				t.sendMessage(api.getLangString("kitMessage").replaceAll("%kit%", args[1].toLowerCase()).replaceAll("%player%", t.getName()));
				p.sendMessage(api.getLangString("kitGive").replaceAll("%kit%", args[1].toLowerCase()).replaceAll("%player%", t.getName()));
				return;
			} else {
				p.sendMessage(api.getLangString("kitNotFound"));
				return;
			}
		}
	}

}
