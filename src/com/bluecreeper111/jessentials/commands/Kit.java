package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.event.cooldownManager;

public class Kit implements CommandExecutor, Listener {

	public static Kit kit;

	public File kitsFile = new File("plugins/JEssentials", "kits.yml");
	public YamlConfiguration kits = YamlConfiguration.loadConfiguration(kitsFile);

	private Main plugin;
	private cooldownManager cooldownManager = new cooldownManager();

	public Kit(Main pl) {
		plugin = pl;
	}

	public void save(Player p, String kit) throws IOException, InvalidConfigurationException {
		kits.load(kitsFile);
		kits.set("Kit." + kit.toLowerCase() + ".armor", p.getInventory().getArmorContents());
		kits.set("Kit." + kit.toLowerCase() + ".items", p.getInventory().getContents());
		kits.set("Kit." + kit.toLowerCase() + ".firstjoin", false);
		kits.save(kitsFile);
	}

	public void delete(String kit) throws IOException, InvalidConfigurationException {
		kits.load(kitsFile);
		if (kits.isSet("Kit." + kit.toLowerCase())) {
			kits.set("Kit." + kit.toLowerCase(), null);
		}
		kits.save(kitsFile);

	}

	public void addInventory(Player p, String kit) throws IOException, InvalidConfigurationException {
		kits.load(kitsFile);
		if (kits.isSet("Kit." + kit.toLowerCase())) {
			try {
				@SuppressWarnings("unchecked")
				ArrayList<ItemStack> d = (ArrayList<ItemStack>) kits.get("Kit." + kit.toLowerCase() + ".items");
				ItemStack[] items = d.toArray(new ItemStack[d.size()]);
				for (int i = 0; i < items.length; i++) {
					ItemStack a = items[i];
					if (a != null) {
						if (p.getInventory().firstEmpty() == -1) {
							World w = p.getWorld();
							w.dropItem(p.getLocation(), a);
						} else {
							p.getInventory().addItem(new ItemStack[] { a });
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String kitMessage = api.getLangString("kitMessage");
		if (!this.kitsFile.exists()) {
			try {
				this.kitsFile.createNewFile();
				this.kits.options().copyDefaults();
				ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
				this.kits.set("Kit.default.items", pickaxe);
				this.kits.save(kitsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".kit")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					if (!kits.getConfigurationSection("Kit").getKeys(true).isEmpty()) {
						String text = "";
						for (String kits : kits.getConfigurationSection("Kit").getKeys(false)) {
							text = text + kits + ", ";
						}
						p.sendMessage("§6Kits: §r" + text);
						return true;
					} else {
						p.sendMessage(api.getLangString("noKits"));
						return true;
					}
				} else {
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("add")) {
							api.incorrectSyntax(p, "/kit add <name>");
							return true;
						} else if (args[0].equalsIgnoreCase("delete")) {
							api.incorrectSyntax(p, "/kit delete <name>");
							return true;
						} else if (args[0].equalsIgnoreCase("firstjoin")) {
							api.incorrectSyntax(p, "/kit firstjoin <name> [true:false]");
							return true;

						} else {
							if (kits.isSet("Kit." + args[0].toLowerCase())) {
								if (kits.isSet("Kit." + args[0].toLowerCase() + ".delay")) {
									int delay = kits.getInt("Kit." + args[0].toLowerCase() + ".delay");
									long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p, args[0].toLowerCase());
									if (p.hasPermission(api.perp() + ".kit.delay.bypass." + args[0].toLowerCase()) || p.hasPermission(api.perp() + ".kit.delay.bypass.*")) {
										if (p.hasPermission(api.perp() + ".kit.*") || p.hasPermission(api.perp() + ".kit." + args[0].toLowerCase())) {
											cooldownManager.setCooldown(p, System.currentTimeMillis(), args[0].toLowerCase());
											try {
												this.addInventory(p, args[0].toLowerCase());
												p.sendMessage(kitMessage.replaceAll("%kit%", args[0]).replaceAll("%player%",
														p.getName().toString()));
												return true;
											} catch (IOException | InvalidConfigurationException e) {
												e.printStackTrace();
											}
											} else {
												api.noPermission(p);
												return true;
											}
									}
									if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= delay) {
										if (p.hasPermission(api.perp() + ".kit.*") || p.hasPermission(api.perp() + ".kit." + args[0].toLowerCase())) {
										cooldownManager.setCooldown(p, System.currentTimeMillis(), args[0].toLowerCase());
										try {
											this.addInventory(p, args[0].toLowerCase());
											p.sendMessage(kitMessage.replaceAll("%kit%", args[0]).replaceAll("%player%",
													p.getName().toString()));
											return true;
										} catch (IOException | InvalidConfigurationException e) {
											e.printStackTrace();
										}
										} else {
											api.noPermission(p);
											return true;
										}
									} else {
										String replace = Long.toString(Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - delay));
										p.sendMessage(api.getLangString("kitCooldown").replaceAll("%kit%", args[0].toLowerCase()).replaceAll("%timeLeft%", replace));
										return true;
									}
								} 
								if (p.hasPermission(api.perp() + ".kit.*")
										|| p.hasPermission(api.perp() + ".kit." + args[0].toLowerCase())) {
									try {
										this.addInventory(p, args[0].toLowerCase());
										p.sendMessage(kitMessage.replaceAll("%kit%", args[0]).replaceAll("%player%",
												p.getName().toString()));
										return true;
									} catch (IOException | InvalidConfigurationException e) {
										e.printStackTrace();
									}
								} else {
									api.noPermission(p);
									return true;
								}
							} else {
								p.sendMessage(api.getLangString("kitNotFound"));
								return true;
							}
						}
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("firstjoin")) {
							api.incorrectSyntax(p, "/kit firstjoin <name> [true:false]");
							return true;
						} else {
							if (args[0].equalsIgnoreCase("add")) {
								if (p.hasPermission(api.perp() + ".kit.add")) {
									if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("delete")
											|| args[1].equalsIgnoreCase("firstjoin") || args[1].equalsIgnoreCase("delay")) {
										p.sendMessage(api.getLangString("kitNoName"));
										return true;
									}
									try {
										this.save(p, args[1].toLowerCase());
										p.sendMessage(api.getLangString("kitCreated").replaceAll("%kit%", args[1]));
										return true;
									} catch (InvalidConfigurationException | IOException e) {
										e.printStackTrace();
									}

								} else {
									api.noPermission(p);
									return true;
								}

							} else if (args[0].equalsIgnoreCase("delete")) {
								if (p.hasPermission(api.perp() + ".kit.delete")) {
									if (kits.isSet("Kit." + args[1].toLowerCase())) {
										try {
											this.delete(args[1].toLowerCase());
											p.sendMessage(api.getLangString("kitDeleted"));
										} catch (IOException | InvalidConfigurationException e) {
											e.printStackTrace();
										}
									} else {
										p.sendMessage(api.getLangString("kitNotFound"));
										return true;
									}
								} else {
									api.noPermission(p);
									return true;
								}
							} else {
								api.incorrectSyntax(p, "/kit [<name>;add;delete;firstjoin;delay]");
								return true;
							}
						}

					} else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("add")) {
							api.incorrectSyntax(p, "/kit add <name>");
							return true;
						}
						if (args[0].equalsIgnoreCase("delete")) {
							api.incorrectSyntax(p, "/kit delete <name>");
							return true;
						}
						if (args[0].equalsIgnoreCase("delay")) {
							if (!p.hasPermission(api.perp() + ".kit.setdelay")) {
								api.noPermission(p);
							} else {
								if (kits.isSet("Kit." + args[1].toLowerCase())) {
									if (api.isInt(args[2])) {
										int delay = Integer.parseInt(args[2]);
										kits.set("Kit." + args[1].toLowerCase() + ".delay", delay);
										try {
											kits.save(kitsFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										p.sendMessage(api.getLangString("kitDelaySet").replaceAll("%time%", Integer.toString(delay)).replaceAll("%kit%", args[0].toLowerCase()));
										return true;
									} else {
										p.sendMessage(api.getLangString("invalidArgumentNumber"));
										return true;
									}
								} else {
									p.sendMessage(api.getLangString("kitNotFound"));
									return true;
								}
								
							}
						}
						if (args[0].equalsIgnoreCase("firstjoin")) {
							if (p.hasPermission(api.perp() + ".kit.firstjoin")) {
								if (kits.isSet("Kit." + args[1].toLowerCase())) {
									if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
										boolean value = Boolean.parseBoolean(args[2]);
										kits.set("Kit." + args[1].toLowerCase() + ".firstjoin", value);
										p.sendMessage(api.getLangString("kitFirstJoin").replaceAll("%kit%", args[1]).replaceAll("%toggle%", Boolean.toString(value)));
										try {
											kits.save(kitsFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										return true;
									} else {
										api.incorrectSyntax(p, "/kit firstjoin <name> [true:false]");
										return true;
									}
								} else {
									p.sendMessage(api.getLangString("kitNotFound"));
									return true;
								}
							} else {
								api.noPermission(p);
								return true;
							}
						} else {
							api.incorrectSyntax(p, "/kit [<name>;add;delete;firstjoin;delay]");
							return true;
						}
					} else {
						api.incorrectSyntax(p, "/kit [<name>;add;delete;firstjoin;delay]");
						return true;
					}
				}

				return true;
			}

		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String firstJoinMessage = ChatColor.translateAlternateColorCodes('&',
				plugin.getConfig().getString("firstJoinMessage"));
		        if (e.getPlayer().getLastPlayed() == 0) {
		        	Bukkit.broadcastMessage(firstJoinMessage.replaceAll("%player%", e.getPlayer().getName().toString()));
					for (String kit : kits.getConfigurationSection("Kit").getKeys(false)) {
						if (kits.getBoolean("Kit." + kit + ".firstjoin") == true) {
							try {
								addInventory(e.getPlayer(), kit);
							} catch (IOException | InvalidConfigurationException e1) {
								e1.printStackTrace();
							}
						}

					}
				}
			}
	}

