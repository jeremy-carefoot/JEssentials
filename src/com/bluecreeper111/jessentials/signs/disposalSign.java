package com.bluecreeper111.jessentials.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class disposalSign implements Listener {
	
	private Main plugin;
	
	public disposalSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String disposalSign = plugin.getConfig().getString("disposalSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[disposal]")) {
			if (p.hasPermission(api.perp() + ".sign.disposal")) {
				e.setLine(0, disposalSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String disposalSign = plugin.getConfig().getString("disposalSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(disposalSign)) {
				Inventory trash = Bukkit.createInventory(null, 54, disposalSign);
				p.openInventory(trash);
			}
		}
	}

}
