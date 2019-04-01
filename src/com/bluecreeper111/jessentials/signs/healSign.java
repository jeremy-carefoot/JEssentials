package com.bluecreeper111.jessentials.signs;

import org.bukkit.attribute.Attribute;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class healSign implements Listener {
	
	private Main plugin;
	
	public healSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String healSign = plugin.getConfig().getString("healSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[heal]")) {
			if (p.hasPermission(api.perp() + ".sign.heal")) {
				e.setLine(0, healSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String healSign = plugin.getConfig().getString("healSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(healSign)) {
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.setFoodLevel(20);
			}
		}
	}

}
