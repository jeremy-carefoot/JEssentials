package com.bluecreeper111.jessentials.commands;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

import org.bukkit.ChatColor;
public class Tpa implements CommandExecutor {

	private Main plugin;

	public Tpa(Main pl) {
		plugin = pl;
	}

	public static HashMap<Player, Player> tpa = new HashMap<Player, Player>();
	public static HashMap<Player, Boolean> tpaHere = new HashMap<Player, Boolean>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		List<String> tpaMessage = plugin.getConfig().getStringList("tpaMessage");
		List<String> tpaHereMessage = plugin.getConfig().getStringList("tpaHereMessage");
		Long value = (plugin.getConfig().getLong("tpaRequestTimedOut") * 20);
		String tpAcceptMessage = api.getLangString("tpAccept");
		String tpDenyMessage = api.getLangString("tpDeny");

		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".tpa")) {
				api.noPermission(p);
				return true;
			} else {
				if (label.equalsIgnoreCase("tpa")) {
					if (args.length == 0) {
						api.incorrectSyntax(p, "/tpa <player>");
						return true;
					} else if (args.length == 1) {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						}
						if (Tptoggle.tpToggled.contains(target)) {
							p.sendMessage(api.getLangString("tpOff"));
							return true;
						}
						tpa.put(target, p);
						tpaHere.put(target, false);
						p.sendMessage(api.getLangString("requestSent"));
						for (String message : tpaMessage) {
							target.sendMessage(ChatColor.translateAlternateColorCodes('&',
									message.replaceAll("%sender%", p.getDisplayName().toString())));
						}
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							
							public void run() {
								if (tpa.containsKey(target)) {
									tpa.remove(target);
									tpaHere.remove(target);
									p.sendMessage(api.getLangString("requestExpired"));
									target.sendMessage(api.getLangString("requestExpired"));
								} else {
									return;
								}
							}

						}, value);
					} else {
						api.incorrectSyntax(p, "/tpa <player>");
						return true;
					}
				} else if(label.equalsIgnoreCase("tpaccept")) {
					if (!p.hasPermission(api.perp() + ".tpaccept")) {
						api.noPermission(p);
						return true;
					} else {
						if (tpa.containsKey(p)) {
							if (tpaHere.get(p) == true) {
								Player other = tpa.get(p);
								other.sendMessage(tpAcceptMessage);
								api.tpDelayEntity(other, p, plugin);
								tpa.remove(p);
								tpaHere.remove(p);
								p.sendMessage(tpAcceptMessage.replaceAll("%player%", p.getDisplayName().toString()));
								return true;
							}
							Player other = tpa.get(p);
							other.sendMessage(tpAcceptMessage);
							api.tpDelayEntity(p, other, plugin);
							tpa.remove(p);
							tpaHere.remove(p);
							p.sendMessage(tpAcceptMessage.replaceAll("%player%", p.getDisplayName().toString()));
							return true;
						} else {
							p.sendMessage(api.getLangString("noRequest"));
							return true;
						}
					}
				} else if(label.equalsIgnoreCase("tpdeny")) {
					if (!p.hasPermission(api.perp() + ".tpdeny")) {
						api.noPermission(p);
						return true;
					} else {
						if (tpa.containsKey(p)) {
							Player other = tpa.get(p);
							other.sendMessage(tpDenyMessage);
							tpa.remove(p);
							tpaHere.remove(p);
							p.sendMessage(tpDenyMessage.replaceAll("%player%", p.getDisplayName().toString()));
						} else {
							p.sendMessage(api.getLangString("noRequest"));
							return true;
						}
					}
				} else if(label.equalsIgnoreCase("tpahere")) {
					if (!p.hasPermission(api.perp() + ".tpahere")) {
						api.noPermission(p);
						return true;
					} else {
						if (args.length == 0) {
							api.incorrectSyntax(p, "/tpahere <player>");
							return true;
						} else {
							Player target = Bukkit.getPlayerExact(args[0]);
							if (target == null) {
								api.pNotFound(p, args[0]);
								return true;
							} else {
								if (Tptoggle.tpToggled.contains(target)) {
									p.sendMessage(api.getLangString("tpOff"));
									return true;
								}
								tpa.put(target, p);
								tpaHere.put(target, true);
								p.sendMessage(api.getLangString("requestSent"));
								for (String message : tpaHereMessage) {
									target.sendMessage(ChatColor.translateAlternateColorCodes('&',
											message.replaceAll("%sender%", p.getDisplayName().toString())));
								}
								scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

									
									public void run() {
										if (tpa.containsKey(target)) {
											tpa.remove(target);
											tpaHere.remove(target);
											p.sendMessage(api.getLangString("requestExpired"));
											target.sendMessage(api.getLangString("requestExpired"));
										} else {
											return;
										}
									}

								}, value);
								
							}
						}
					}
				} else {
					api.incorrectSyntax(p, "/" + label + "<player>");
					return true;
				}
			}
		}
		return true;

	}

}
