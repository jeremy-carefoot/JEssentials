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

import net.milkbowl.vault.economy.EconomyResponse;

public class buySign implements Listener {
	
	private Main plugin;
	
	public buySign(Main pl) {
		plugin = pl;
	}
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		String buySign = plugin.getConfig().getString("buySign").replaceAll("&", "§");
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[buy]")) {
			if (p.hasPermission(api.perp() + ".sign.buy")) {
				e.setLine(0, buySign);
			}
		}
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		String buySign = plugin.getConfig().getString("buySign").replaceAll("&", "§");
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof Sign)) {
			Sign sign = (Sign)e.getClickedBlock().getState();
			if (sign.getLine(0).equals(buySign)) {
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
				ItemStack buy = new ItemStack(item, Integer.parseInt(sign.getLine(1)));
				EconomyResponse response = Main.getEconomy().withdrawPlayer(e.getPlayer(), Double.parseDouble(sign.getLine(3)));
				if (response.transactionSuccess()) {
					api.addItem(e.getPlayer(), buy);
					e.getPlayer().sendMessage(api.getLangString("moneyLost").replaceAll("%amount%", Main.getEconomy().currencyNamePlural() + sign.getLine(3)));
				} else {
					e.getPlayer().sendMessage(api.getLangString("noMoney"));
					return;
				}
			}
		}
	}

}
