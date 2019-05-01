package com.bluecreeper111.jessentials.commands;



import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Afk implements CommandExecutor, Listener {
	
	private Main plugin;
	
	public Afk(Main pl) {
		plugin = pl;
	}
	
	public HashMap<Player, Integer> afkmove = new HashMap<Player, Integer>();
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean broadcastAfkEnable = plugin.getConfig().getBoolean("enable-broadcastAfk");
		String afkBroadcast = api.getLangString("afkBroadcast");
		String afkBroadcastNo = api.getLangString("notAfkBroadcast");
		if (!(sender instanceof Player)) {
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/afk <player>");
				return true;
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				}
				api.loadPlayerData();
				if (api.getPlayerData().getBoolean(target.getName() + ".afk") == true) {
					api.getPlayerData().set(target.getName() + ".afk", false);
					api.savePlayerData();
					if (broadcastAfkEnable == true) {
						Bukkit.broadcastMessage(afkBroadcastNo.replaceAll("%player%", target.getDisplayName()));
					} else {
						sender.sendMessage(api.getLangString("notAfkSender").replaceAll("%player%", target.getDisplayName()));
						target.sendMessage(api.getLangString("notAfk"));
					}
					return true;
				}
				if (api.getPlayerData().getBoolean(target.getName() + ".afk") == false) {
					api.getPlayerData().set(target.getName() + ".afk", true);
					api.savePlayerData();
					if (broadcastAfkEnable == true) {
						Bukkit.broadcastMessage(afkBroadcast.replaceAll("%player%", target.getDisplayName()));
						return true;
					} else {
						sender.sendMessage(api.getLangString("AfkSender").replaceAll("%player%", target.getDisplayName()));
						target.sendMessage(api.getLangString("Afk"));
						return true;
					}
				}
				return true;
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (!p.hasPermission(api.perp() + ".afk")) {
					api.noPermission(p);
					return true;
				} else {
					api.loadPlayerData();
					if (api.getPlayerData().getBoolean(p.getName() + ".afk") == true) {
						api.getPlayerData().set(p.getName() + ".afk", false);
						api.savePlayerData();
						if (broadcastAfkEnable == true) {
							Bukkit.broadcastMessage(afkBroadcastNo.replaceAll("%player%", p.getDisplayName()));
						} else {
							p.sendMessage(api.getLangString("notAfk"));
						}
						return true;
					}
					if (api.getPlayerData().getBoolean(p.getName() + ".afk") == false) {
						api.getPlayerData().set(p.getName() + ".afk", true);
						api.savePlayerData();
						if (broadcastAfkEnable == true) {
							Bukkit.broadcastMessage(afkBroadcast.replaceAll("%player%", p.getDisplayName()));
							return true;
						} else {
							p.sendMessage(api.getLangString("Afk"));
							return true;
						}
					}
					return true;
				}
			} else if (args.length == 1) {
				if (!p.hasPermission(api.perp() + ".afk.others")) {
					api.noPermission(p);
					return true;
				} else {
					api.loadPlayerData();
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						if (api.getPlayerData().getBoolean(target.getName() + ".afk") == true) {
							api.getPlayerData().set(target.getName() + ".afk", false);
							api.savePlayerData();
							if (broadcastAfkEnable == true) {
								Bukkit.broadcastMessage(afkBroadcastNo.replaceAll("%player%", target.getDisplayName()));
								return true;
							} else {
								target.sendMessage(api.getLangString("notAfk"));
								p.sendMessage(api.getLangString("notAfkSender").replaceAll("%player%", target.getDisplayName()));
								return true;
							}
						}
						if (api.getPlayerData().getBoolean(target.getName() + ".afk") == false) {
							api.getPlayerData().set(target.getName() + ".afk", true);
							api.savePlayerData();
							if (broadcastAfkEnable == true) {
								Bukkit.broadcastMessage(afkBroadcast.replaceAll("%player%", target.getDisplayName()));
								return true;
							} else {
								target.sendMessage(api.getLangString("Afk"));
								p.sendMessage(api.getLangString("AfkSender").replaceAll("%player%", target.getDisplayName()));
								return true;
							}
						}
						return true;
					}
				}
			} else {
				api.incorrectSyntax(p, "/afk");
				return true;
			}
		}
	}
	public void resetTimer(Player p) {
		boolean enableKick = plugin.getConfig().getBoolean("enable-afkAutoKick");
		long kickTime = (plugin.getConfig().getInt("afkKickTime")) * 1200L;
		afkmove.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Bukkit.getPlayerExact(p.getName()) == null) {
					return;
				} else {
					if (enableKick == true) {
						if (p.hasPermission(api.perp() + ".afk.kickexempt")) {
							return;
						}
						p.kickPlayer(api.getLangString("afkKick"));
						return;
					} else {
						if (!p.hasPermission(api.perp() + ".afk.auto")) {
							return;
						} else {
							if (api.getPlayerData().getBoolean(p.getName() + ".afk") == false) {
								api.getPlayerData().set(p.getName() + ".afk", true);
								api.savePlayerData();
								if (plugin.getConfig().getBoolean("enable-broadcastAfk") == true) {
									String afkBroadcast = api.getLangString("afkBroadcast");
									Bukkit.broadcastMessage(afkBroadcast.replaceAll("%player%", p.getDisplayName()));
									return;
								} else {
									p.sendMessage(api.getLangString("Afk"));
									return;
								}
							}
							return;
						}
					}
				}
			}
			
		}, kickTime));
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		this.resetTimer(e.getPlayer());
	}
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (afkmove.containsKey(e.getPlayer())) {
			Bukkit.getScheduler().cancelTask(afkmove.get(e.getPlayer()));
		}
		this.resetTimer(e.getPlayer());
		if (api.getPlayerData().getBoolean(e.getPlayer().getName() + ".afk") == true) {
			api.getPlayerData().set(e.getPlayer().getName() + ".afk", false);
			api.savePlayerData();
			if (plugin.getConfig().getBoolean("enable-broadcastAfk") == true) {
				String afkBroadcastNo = api.getLangString("notAfkBroadcast");
				Bukkit.broadcastMessage(afkBroadcastNo.replaceAll("%player%", e.getPlayer().getDisplayName()));
				return;
			} else {
				e.getPlayer().sendMessage(api.getLangString("notAfk"));
				return;
			}
		}
		
	}

}
