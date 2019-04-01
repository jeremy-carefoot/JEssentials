package com.bluecreeper111.jessentials.signs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;


public class freeSign implements Listener {
	
	private Main plugin;
	
	public freeSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String freeSign = plugin.getConfig().getString("freeSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[free]")) {
			if (p.hasPermission(api.perp() + ".sign.free")) {
				e.setLine(0, freeSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String freeSign = plugin.getConfig().getString("freeSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(freeSign)) {
				Material item = Material.getMaterial(sign.getLine(1).toUpperCase());
				if (item == null) {
					sign.setLine(2, "§cInvalid Item");
					return;
				}
				Inventory free = Bukkit.createInventory(null, 27, freeSign);
				for (int i = 0; i < free.getSize(); i++) {
					free.setItem(i, new ItemStack(item, 64));
				}
				p.openInventory(free);
			}
		}
	}

}
