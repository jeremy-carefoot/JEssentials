package com.bluecreeper111.jessentials.event;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerDeath implements Listener {
	
	public static HashMap<String, Location> deathInfo = new HashMap<String, Location>();
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			deathInfo.put(p.getName(), p.getLocation());
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		deathInfo.remove(e.getPlayer().getName());
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		deathInfo.remove(e.getPlayer().getName());
	}

}
