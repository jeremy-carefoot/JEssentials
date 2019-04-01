package com.bluecreeper111.jessentials.api;

import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.bluecreeper111.jessentials.Main;

public class teleportSafety implements Listener {

	public static HashSet<String> tpSafety = new HashSet<String>();

	private Main plugin;

	public teleportSafety(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player && tpSafety.contains(e.getName())) {
			if (plugin.getConfig().getBoolean("enable-tpSafety") == true) {
				event.setCancelled(true);
			}
		}
	}

}
