package com.bluecreeper111.jessentials.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.bluecreeper111.jessentials.api.api;

public class playerGamemode implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().toLowerCase().startsWith("/gamemode")) {
			Player player = event.getPlayer();
			if (!player.hasPermission(api.perp() + ".gamemode")) {
				api.noPermission(player);
				event.setCancelled(true);
			} 
		}
	}

}
