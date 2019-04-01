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

public class warpSign implements Listener {
	
	private Main plugin;
	
	public warpSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String warpSign = plugin.getConfig().getString("warpSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[warp]")) {
			if (p.hasPermission(api.perp() + ".sign.warp")) {
				e.setLine(0, warpSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String warpSign = plugin.getConfig().getString("warpSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(warpSign)) {
				Bukkit.getServer().dispatchCommand(p, "warp " + sign.getLine(1));
			}
		}
	}

}
