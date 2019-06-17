package com.bluecreeper111.jessentials.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.event.playerDeath;

public class api extends Main {

	public static HashMap<String, Boolean> tpDelayCancelled = new HashMap<String, Boolean>();

	public static void noPermission(Player player) {
		player.sendMessage(api.getLangString("noPermission"));
	}

	public static void pNotFound(Player player, String args) {
		player.sendMessage(api.getLangString("pNotFound").replaceAll("%target%", args));
	}

	public static void pNotFoundConsole(String args) {
		Logger logger = Bukkit.getLogger();
		logger.info(api.getLangString("pNotFound").replaceAll("%target%", args));
	}

	public static void notPlayer() {
		Logger logger = Bukkit.getLogger();
		logger.info(api.getLangString("notPlayer"));
	}

	public static void incorrectSyntax(Player player, String syntax) {
		player.sendMessage(api.getLangString("incorrectSyntax").replaceAll("%syntax%", syntax));
	}

	public static void incorrectSyntaxConsole(String syntax) {
		Logger logger = Bukkit.getLogger();
		logger.info(api.getLangString("incorrectSyntax").replaceAll("%syntax%", syntax));
	}

	public static void tpDelayLoc(Location loc, Player p, Main plugin) {
		if (tpDelayEnable == true) {
			if (p.hasPermission(api.perp() + ".tpdelay.bypass")) {
				playerDeath.deathInfo.put(p.getName(), p.getLocation());
				p.teleport(loc);
				tpSafetyLoc(p, plugin);
				p.sendMessage(api.getLangString("teleportMessage"));
				return;
			}
			tpDelayCancelled.put(p.getName(), false);
			teleportDelay.tpDelayPlayers.add(p.getName());
			p.sendMessage(api.getLangString("tpDelayMessage").replaceAll("%delay%", Long.toString(tpDelay)));
			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					teleportDelay.tpDelayPlayers.remove(p.getName());
					if (tpDelayCancelled.get(p.getName()) == false) {
						playerDeath.deathInfo.put(p.getName(), p.getLocation());
						p.teleport(loc);
						tpSafetyLoc(p, plugin);
						p.sendMessage(api.getLangString("teleportMessage"));
						return;
					} else {
						p.sendMessage(api.getLangString("teleportCancelled"));
						return;
					}
				}
			}, tpDelay);

		} else {
			playerDeath.deathInfo.put(p.getName(), p.getLocation());
			p.teleport(loc);
			tpSafetyLoc(p, plugin);
			p.sendMessage(api.getLangString("teleportMessage"));
		}
	}

	public static void tpSafetyLoc(Player p, Main plugin) {
		teleportSafety.tpSafety.add(p.getName());
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				teleportSafety.tpSafety.remove(p.getName());
			}
		}, tpSafetyLength);
	}
	
	public static String replacePlayer(String message, Player p) {
		return message.replaceAll("%player%", p.getName().toString());
	}
	
	public static void tpDelayEntity(Player to, Player p, Main plugin) {
		if (tpDelayEnable == true) {
			if (p.hasPermission(api.perp() + "tpdelay.bypass")) {
				playerDeath.deathInfo.put(p.getName(), p.getLocation());
				p.teleport(to);
				tpSafetyLoc(p, plugin);
				p.sendMessage(api.getLangString("teleportMessage"));
				return;
			}
			tpDelayCancelled.put(p.getName(), false);
			teleportDelay.tpDelayPlayers.add(p.getName());
			p.sendMessage(api.getLangString("tpDelayMessage").replaceAll("%delay%", Long.toString(tpDelay)));
			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					teleportDelay.tpDelayPlayers.remove(p.getName());
					if (tpDelayCancelled.get(p.getName()) == false) {
						playerDeath.deathInfo.put(p.getName(), p.getLocation());
						p.teleport(to);
						tpSafetyLoc(p, plugin);
						p.sendMessage(api.getLangString("teleportMessage"));
						return;
					} else {
						p.sendMessage(api.getLangString("teleportCancelled"));
						return;
					}
				}
			}, tpDelay);

		} else {
			playerDeath.deathInfo.put(p.getName(), p.getLocation());
			p.teleport(to);
			tpSafetyLoc(p, plugin);
			p.sendMessage(api.getLangString("teleportMessage"));
		}
	}
	
	public static void loadPlayerData() {
		try {
			Main.playerData.load(Main.playerDataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return;
	}
	public static void savePlayerData() {
		try {
			Main.playerData.save(Main.playerDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public static YamlConfiguration getPlayerData() {
		return Main.playerData;
	}
	public static void sendColorMsg(Player p, String message) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		return;
	}
	public static String translateColor(String translate) {
		return ChatColor.translateAlternateColorCodes('&', translate);
	}
	public static OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (name.equals(player.getName())) {
				return player;
			}
		}
		return null;
	}
	public static boolean isInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	public static boolean isDouble(String number) {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	public static YamlConfiguration getLanguage() {
		return com.bluecreeper111.jessentials.api.Language.language;
	}
	public static String getLangString(String path) {
		if (com.bluecreeper111.jessentials.api.Language.language.getString("l." + path) == null) {
			Bukkit.getLogger().severe("Error occured: Couldn't find message " + path);
		}
		return api.translateColor(com.bluecreeper111.jessentials.api.Language.language.getString("l." + path));
	}
	public static String perp() {
		return Main.permissionPrefix;
	}
	public static boolean isLong(String number) {
		try {
			Long.parseLong(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	public static void addItem(Player p, ItemStack item) {
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItem(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
	}
}
