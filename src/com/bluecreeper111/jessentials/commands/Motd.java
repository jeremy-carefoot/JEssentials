package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

import me.clip.placeholderapi.PlaceholderAPI;

public class Motd implements CommandExecutor {

	private Main plugin;

	public Motd(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				this.sendMotd(sender);
				return true;
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".motd")) {
					api.noPermission(p);
					return true;
				} else {
					this.sendMotd(sender);
					return true;
				}
			}

		} else if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
			if (!(sender instanceof Player)) {
				Logger logger = Bukkit.getLogger();
				if (!(args.length > 1)) {
					api.incorrectSyntaxConsole("/motd set <message>");
					return true;
				} else {
					String text = "";
					for (int i = 1; i < args.length; i++) {
						text = text + api.translateColor(args[i]) + " ";
					}
					plugin.getConfig().set("motd", text);
					plugin.saveConfig();
					logger.info(api.getLangString("motdSet") + " §r" + text);
					return true;
				}
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".motd.set")) {
					api.noPermission(p);
					return true;
				} else {
					if (!(args.length > 1)) {
						api.incorrectSyntax(p, "/motd set <message>");
						return true;
					} else {
						String text = "";
						for (int i = 1; i < args.length; i++) {
							text = text + api.translateColor(args[i]) + " ";
						}
						plugin.getConfig().set("motd", text);
						plugin.saveConfig();
						p.sendMessage(api.getLangString("motdSet") + " §r" + text);
						return true;
					}
				}
			}
		} else if (args.length > 0 && args[0].equalsIgnoreCase("enable")) {
			if (!(sender instanceof Player)) {
				Logger logger = Bukkit.getLogger();
				if (!(args.length == 2)) {
					api.incorrectSyntaxConsole("/motd enable [true:false]");
					return true;
				} else {
					if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
						boolean value = Boolean.parseBoolean(args[1]);
						plugin.getConfig().set("enable-motd", value);
						plugin.saveConfig();
						logger.info(api.getLangString("motdEnable") + " §c" + args[1]);
						return true;
					} else {
						api.incorrectSyntaxConsole("/motd enable [true:false]");
						return true;
					}
				}
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".motd.enable")) {
					api.noPermission(p);
					return true;
				} else {
					if (args.length != 2) {
						api.incorrectSyntax(p, "/motd enable [true:false]");
						return true;
					} else {
						if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
							boolean value = Boolean.parseBoolean(args[1]);
							plugin.getConfig().set("enable-motd", value);
							plugin.saveConfig();
							p.sendMessage(api.getLangString("motdEnable") + " §c" + args[1]);
							return true;
							
						} else {
							api.incorrectSyntax(p, "/motd enable [true:false]");
							return true;
						}
					}
				}
			}
		}
		return true;
	}
	public void sendMotd(CommandSender sender) {
		String message = plugin.getConfig().getString("motd");
			if (Main.pApi && sender instanceof Player) {
				message = PlaceholderAPI.setPlaceholders((Player)sender, message);
			}
			message = ChatColor.translateAlternateColorCodes('&', message);
			sender.sendMessage(message.replaceAll("%player%", sender.getName()));
		}
	}


