package com.bluecreeper111.jessentials.event;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.commands.Mute;

import me.clip.placeholderapi.PlaceholderAPI;

public class playerChat implements Listener {

	private Main plugin;

	public playerChat(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		try {
			Main.playerData.load(Main.playerDataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Player p = event.getPlayer();
		if (p.hasPermission(api.perp() + ".chat.color")) {
			event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		}
		if (event.getMessage().contains("\\")) {
			event.setMessage(event.getMessage().replaceAll("\\", "\\\\"));
		}
		String messageFormat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chat-format"));
		String group = "null";
		String pDisplayName = "null";
		group = Main.getChat().getPrimaryGroup(p);
		pDisplayName = Main.getChat().getPlayerPrefix(p).replaceAll("&", "§") + p.getDisplayName() + Main.getChat().getPlayerSuffix(p).replaceAll("&", "§");
		String prefix = Main.getChat().getGroupPrefix(p.getWorld().getName(), group);
		messageFormat = messageFormat.replaceAll("%player%", p.getName().toString());
		messageFormat = messageFormat.replaceAll("%playerDisplay%", pDisplayName != null ? pDisplayName : p.getDisplayName());
		messageFormat = messageFormat.replaceAll("%message%", event.getMessage());
		messageFormat = messageFormat.replaceAll("%world%", p.getWorld().getName());
		messageFormat = messageFormat.replaceAll("%group%", api.translateColor(prefix != null ? prefix : ""));
		if (Main.pApi) {
		messageFormat = PlaceholderAPI.setPlaceholders(p, messageFormat);
		}
		event.setFormat(messageFormat);
		if (Mute.muted.contains(p.getName())) {
			p.sendMessage(api.getLangString("muted"));
			event.setCancelled(true);
		}
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (Main.playerData.getStringList(player.getName() + ".ignored-players").contains(p.getName())) {
				if (!p.hasPermission(api.perp() + ".ignore.bypass")) {
					event.getRecipients().remove(player);
					return;
				}
				player.sendMessage(api.getLangString("ignoreBypass").replaceAll("%player%", event.getPlayer().getName()));
			}
		}

	}

}
