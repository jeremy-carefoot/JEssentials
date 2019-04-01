package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;



public class SetHome implements CommandExecutor {
	private Main plugin;

	public SetHome(Main pl) {
		plugin = pl;
	}
	
	public static SetHome homeset;

	public static File homesFile = new File("plugins//JEssentials//homes.yml");
	public static YamlConfiguration homes = YamlConfiguration.loadConfiguration(homesFile);

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int maxHomes = plugin.getConfig().getInt("homeNumber");
		String setHomeMessage = api.getLangString("setHomeMessage");
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		}
		Player p = (Player) sender;
		int houseNumber = homes.getInt(p.getName() + ".homeNumber");
		int houseNumber1 = houseNumber + 1;
		if (!p.hasPermission(api.perp() + ".sethome")) {
			api.noPermission(p);
			return true;
		} else {
			if (p.hasPermission(api.perp() + ".sethome.multiple")) {
				
				if (args.length == 0) {
					api.incorrectSyntax(p, "/sethome <name>");
					return true;
				}
				if (houseNumber >= maxHomes) {
					p.sendMessage(api.getLangString("maxHomes"));
					return true;
				}
				if (args[0].equals("homeNumber")) {
					p.sendMessage(api.getLangString("invalidHomeName"));
					return true;
				}
				
					Location loc = p.getLocation();
					double x = loc.getX();
					double y = loc.getY();
					double z = loc.getZ();
					double yaw = loc.getYaw();
					double pitch = loc.getPitch();
					String world = p.getWorld().getName();
					homes.set(p.getName() + ".homeNumber", Integer.valueOf(houseNumber1));
					homes.set(p.getName() + "." + args[0] + ".world", world);
					homes.set(p.getName() + "." + args[0] + ".x", Double.valueOf(x));
					homes.set(p.getName() + "." + args[0] + ".y", Double.valueOf(y));
					homes.set(p.getName() + "." + args[0] + ".z", Double.valueOf(z));
					homes.set(p.getName() + "." + args[0] + ".yaw", Double.valueOf(yaw));
					homes.set(p.getName() + "." + args[0] + ".pitch", Double.valueOf(pitch));
					try {
						homes.save(homesFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					p.sendMessage(setHomeMessage.replaceAll("%player%", p.getName()));
					return true;

			} else {
				
				if (houseNumber >= 1) {
					p.sendMessage(api.getLangString("maxHomes"));
					return true;
				}
				Location loc = p.getLocation();
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				double yaw = loc.getYaw();
				double pitch = loc.getPitch();
				String world = p.getWorld().getName();
				homes.set(p.getName() + ".homeNumber", Integer.valueOf(houseNumber1));
				homes.set(p.getName() + "." + "home." + "world", world);
				homes.set(p.getName() + "." + "home." + "x", Double.valueOf(x));
				homes.set(p.getName() + "." + "home." + "y", Double.valueOf(y));
				homes.set(p.getName() + "." + "home." + "z", Double.valueOf(z));
				homes.set(p.getName() + "." + "home." + "yaw", Double.valueOf(yaw));
				homes.set(p.getName() + "." + "home." + "pitch", Double.valueOf(pitch));
				try {
					homes.save(homesFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.sendMessage(setHomeMessage.replaceAll("%player%", p.getName().toString()));
				return true;
			}
		}
	}

}
