package com.bluecreeper111.jessentials.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class kitSign implements Listener {
	
	private Main plugin;
	
	public kitSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String kitSign = plugin.getConfig().getString("kitSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[kit]")) {
			if (p.hasPermission(api.perp() + ".sign.kit")) {
				e.setLine(0, kitSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String kitSign = plugin.getConfig().getString("kitSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(kitSign)) {
				Bukkit.getServer().dispatchCommand(p, "kit " + sign.getLine(1));
			}
		}
	}

}
