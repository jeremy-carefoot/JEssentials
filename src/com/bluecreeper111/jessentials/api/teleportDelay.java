package com.bluecreeper111.jessentials.api;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class teleportDelay implements Listener {

	public static HashSet<String> tpDelayPlayers = new HashSet<String>();

	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (p.getLocation().getX() != event.getTo().getX() || p.getLocation().getY() != event.getTo().getY()
				|| p.getLocation().getZ() != event.getTo().getZ()) {
			if (tpDelayPlayers.contains(p.getName())) {
				api.tpDelayCancelled.put(p.getName(), true);
			}

		}
		
	}

}
