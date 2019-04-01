package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class SetSpawn implements CommandExecutor {
	
	public static File spawnFile = new File("plugins/JEssentials", "spawn.yml");
	public static YamlConfiguration spawn = YamlConfiguration.loadConfiguration(spawnFile);
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".setspawn")) {
				api.noPermission(p);
				return true;
			} else {
				double x = p.getLocation().getX();
				double y = p.getLocation().getY();
				double z = p.getLocation().getZ();
				double pitch = p.getLocation().getPitch();
				double yaw = p.getLocation().getYaw();
				String world = p.getWorld().getName();
				spawn.set("Spawn.world", world);
				spawn.set("Spawn.x", Double.valueOf(x));
				spawn.set("Spawn.y", Double.valueOf(y));
				spawn.set("Spawn.z", Double.valueOf(z));
				spawn.set("Spawn.pitch", Double.valueOf(pitch));
				spawn.set("Spawn.yaw", Double.valueOf(yaw));
				p.sendMessage(api.getLangString("spawnSet"));
				try {
					spawn.save(spawnFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
	}

}
