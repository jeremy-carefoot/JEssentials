package com.bluecreeper111.jessentials.event;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class playerGive implements Listener {
	
	private Main plugin;
	public playerGive(Main pl) {
		plugin = pl;
	}
	public HashSet<Player> reminded = new HashSet<Player>();
	
	@EventHandler
	public void onPlayerGive(PlayerCommandPreprocessEvent event) {
		if (Main.economyEnabled == false) {
			if (event.getMessage().startsWith("/economy") || event.getMessage().startsWith("/eco") || event.getMessage().startsWith("/balance")
					|| event.getMessage().startsWith("/bal") || event.getMessage().startsWith("/money") || event.getMessage().startsWith("/baltop")
					|| event.getMessage().startsWith("/pay") || event.getMessage().startsWith("/balancetop")) {
				event.getPlayer().sendMessage(api.getLangString("noEconomy"));
				event.setCancelled(true);
			}
		}
		String loginMailFull = api.getLangString("mailFull");
		if (event.getMessage().toLowerCase().startsWith("/give")) {
			Player player = event.getPlayer();
			if (!player.hasPermission(api.perp() + ".give")) {
				event.setCancelled(true);
				api.noPermission(player);
			} 
		}
		Player p = event.getPlayer();
		if (api.getPlayerData().isSet(p.getName() + ".mail")) {
			if (reminded.contains(p)) {
				return;
			}
			int messages = api.getPlayerData().getConfigurationSection(p.getName() + ".mail").getKeys(false).size();
			p.sendMessage(loginMailFull.replaceAll("%messages%", Integer.toString(messages)).replaceAll("%player%", p.getName()));
			reminded.add(p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					reminded.remove(p);
				}
			}, 7500L);
		
		}
		
	}

}
