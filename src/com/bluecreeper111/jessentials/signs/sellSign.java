package com.bluecreeper111.jessentials.signs;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class sellSign implements Listener {
	
	private Main plugin;
	
	public sellSign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String sellSign = plugin.getConfig().getString("sellSign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[sell]")) {
			if (p.hasPermission(api.perp() + ".sign.sell")) {
				e.setLine(0, sellSign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String sellSign = plugin.getConfig().getString("sellSign").replaceAll("&", "§");
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(sellSign)) {
				Material item = Material.getMaterial(sign.getLine(2).toUpperCase());
				if (item == null) {
					e.getPlayer().sendMessage(api.getLangString("invalidSignItem"));
					return;
				}
				if(!api.isInt(sign.getLine(1))) {
					e.getPlayer().sendMessage(api.getLangString("invalidSignAmount"));
					return;
				}
				if(!api.isDouble(sign.getLine(3))) {
					e.getPlayer().sendMessage(api.getLangString("invalidSignPrice"));
					return;
				}
				ItemStack sell = new ItemStack(item, Integer.parseInt(sign.getLine(1)));
				if (e.getPlayer().getInventory().contains(sell)) {
					e.getPlayer().getInventory().removeItem(sell);
					Main.getEconomy().depositPlayer(e.getPlayer(), Double.parseDouble(sign.getLine(3)));
					e.getPlayer().sendMessage(api.getLangString("moneyGain").replaceAll("%amount%", Main.getEconomy().currencyNamePlural() + sign.getLine(3)));
					return;
				} else {
					e.getPlayer().sendMessage(api.getLangString("signSellInsufficient"));
					return;
				}
			}
		}
	}

}
