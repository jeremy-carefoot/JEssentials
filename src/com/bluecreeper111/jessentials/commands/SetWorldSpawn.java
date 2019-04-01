package com.bluecreeper111.jessentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class SetWorldSpawn implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".setworldspawn")) {
				api.noPermission(p);
				return true;
			} else {
				World world = p.getWorld();
				world.setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				p.sendMessage(api.getLangString("worldSpawnSet"));
				return true;
			}
		}
	}
	

}
