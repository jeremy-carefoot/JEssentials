package com.bluecreeper111.jessentials.commands;

import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Mute implements CommandExecutor {

	public static HashSet<String> muted = new HashSet<String>();
	private Main plugin;

	public Mute(Main pl) {
		plugin = pl;
	}

	public boolean isLong(String n) {
		try {
			Long.parseLong(n);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BukkitScheduler timer = plugin.getServer().getScheduler();
		String mutedMessage = api.getLangString("muted");
		String unmutedMessage = api.getLangString("unmuted");
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length > 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (this.isLong(args[1])) {
						Long value = Long.parseLong(args[1]);
						logger.info(api.getLangString("mutedSenderTimed").replaceAll("%player%", target.getName()).replaceAll("%mins%", Long.toString(value)));
						value = value * 1200L;
						muted.add(target.getName());
						target.sendMessage(mutedMessage.replaceAll("%player%", target.getName().toString()));
						timer.scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								if (muted.contains(target.getName())) {
									muted.remove(target.getName());
									target.sendMessage(unmutedMessage.replaceAll("%player%", target.getName()));
									return;
								} else {
									return;
								}
							}

						}, value);

					} else {
						api.incorrectSyntaxConsole("/mute <player> <time in minutes>");
						return true;
					}
				}
			} else {
				api.incorrectSyntaxConsole("/mute <player> <time in minutes>");
				return true;
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".mute")) {
				api.noPermission(p);
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFound(p, args[0]);
					return true;
				} else {
					if (target.hasPermission(api.perp() + ".mute.exempt")) {
						p.sendMessage(api.getLangString("cannotMute"));
						return true;
					}
					if (muted.contains(target.getName())) {
						muted.remove(target.getName());
						target.sendMessage(unmutedMessage.replaceAll("%player%", target.getName().toString()));
						p.sendMessage(api.getLangString("mutedSender").replaceAll("%player%", target.getName()));
						return true;
					}
					if (!muted.contains(target.getName())) {
						muted.add(target.getName());
						target.sendMessage(mutedMessage.replaceAll("%player%", target.getName().toString()));
						p.sendMessage(api.getLangString("unmutedSender").replaceAll("%player%", target.getName()));
						return true;
					}
				}
			} else if (args.length > 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFound(p, args[0]);
					return true;
				} else {
					if (target.hasPermission(api.perp() + ".mute.exempt")) {
						p.sendMessage(api.getLangString("cannotMute"));
						return true;
					} else {
						if (!muted.contains(target.getName())) {
							if (this.isLong(args[1])) {
								Long value = Long.parseLong(args[1]);
								p.sendMessage(api.getLangString("mutedSenderTimed").replaceAll("%player%", target.getName()).replaceAll("%mins%", Long.toString(value)));
								target.sendMessage(mutedMessage.replaceAll("%player%", target.getName().toString()));
								value = value * 1200L;
								muted.add(target.getName());
								timer.scheduleSyncDelayedTask(plugin, new Runnable() {
									public void run() {
										if (muted.contains(target.getName())) {
											muted.remove(target.getName());
											target.sendMessage(
													unmutedMessage.replaceAll("%player%", target.getName().toString()));
											return;
										} else {
											return;
										}

									}
								}, value);
							}
						} else {
							muted.remove(target.getName());
							target.sendMessage(unmutedMessage.replaceAll("%player%", target.getName().toString()));
							p.sendMessage(api.getLangString("unmutedSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			} else {
				api.incorrectSyntax(p, "/mute <player> <time in minutes>");
				return true;
			}
		}
		return true;

	}

}
