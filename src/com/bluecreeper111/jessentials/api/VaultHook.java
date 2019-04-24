package com.bluecreeper111.jessentials.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

import com.bluecreeper111.jessentials.Main;

import net.milkbowl.vault.economy.Economy;

public class VaultHook {
	
	private Main plugin;
	private Economy provider;
	
	public VaultHook(Main pl) {
		plugin = pl;
	}
	
	public void hook() {
		provider = plugin.economyImplementer;
		Bukkit.getServicesManager().register(Economy.class, this.provider, this.plugin, ServicePriority.Normal);
	}
	public void unhook() {
		Bukkit.getServicesManager().unregister(Economy.class, this.provider);
		
	}
	

}
