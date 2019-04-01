package com.bluecreeper111.jessentials.event;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class commandCooldown implements Listener {
	
	private Main plugin;
	private cooldownManager cooldownManager = new cooldownManager();
	
	public commandCooldown(Main pl) {
		plugin = pl;
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void commandSend(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String commandSent = e.getMessage();
		if (!plugin.getConfig().getStringList("commandCooldowns").isEmpty()) {
			for (String t : plugin.getConfig().getStringList("commandCooldowns")) {
				String[] command = t.split(",");
				if (p.hasPermission(api.perp() + ".cooldown.bypass.*")) {
					return;
				}
				if (!api.isLong(command[1].trim())) {
					Bukkit.getLogger().severe("[JEssentials] Configuration error! Check commandCooldowns");
					return;
				}
				
				if (commandSent.equalsIgnoreCase(command[0])) {
					int time = Integer.parseInt(command[1].trim());
					if (p.hasPermission(api.perp() + ".cooldown.bypass." + command[0].replaceFirst("/", ""))) {
						return;
					}
					long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p, commandSent);
					if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= time) {
						cooldownManager.setCooldown(p, System.currentTimeMillis(), commandSent);
						return;
					} else {
						String replace = Long.toString(Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - time));
						p.sendMessage(api.getLangString("onCooldown").replaceAll("%command%", command[0]).replaceAll("%timeLeft%", replace));
						e.setCancelled(true);
						return;
					}
				}
			}
		} else {
			return;
		}
		
	}

}
