package com.bluecreeper111.jessentials.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Home implements CommandExecutor {
	
	private Main plugin;
	
	public Home(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!SetHome.homesFile.exists()) {
			try {
				SetHome.homes.save(SetHome.homesFile);
			} catch (IOException localIOException) {
			}
		}
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			int homeNumber = SetHome.homes.getInt(p.getName() + ".homeNumber");
			if (!p.hasPermission(api.perp() + ".home")) {
				api.noPermission(p);
				return true;
			}
			if (!SetHome.homes.isSet(p.getName() + ".homeNumber")) {
				p.sendMessage(api.getLangString("noHome"));
				return true;
			}
			
			if (args.length == 0) {
				if (homeNumber == 1) {
					if (SetHome.homes.isSet(p.getName() + ".home.world")) {
						double x = SetHome.homes.getDouble(p.getName() + ".home.x");
						double y = SetHome.homes.getDouble(p.getName() + ".home.y");
						double z = SetHome.homes.getDouble(p.getName() + ".home.z");
						double yaw = SetHome.homes.getDouble(p.getName() + ".home.yaw");
						double pitch = SetHome.homes.getDouble(p.getName() + ".home.pitch");
						String world = SetHome.homes.getString(p.getName()+ ".home.world");
						Location loc = new Location(Bukkit.getWorld(world), x, y, z);
						loc.setYaw((float)yaw);
						loc.setPitch((float)pitch);
						api.tpDelayLoc(loc, p, plugin);
						return true;
					} else {
						String text = "";
						for (String home : SetHome.homes.getConfigurationSection(p.getName()).getKeys(false)) {
							if (home.equals("homeNumber")) {
								home = "§b(" + api.getLangString("homeNumber") + homeNumber + ")§r";
							}
							text = text + home + ", ";
						}
						p.sendMessage("§6Homes: §r" + text);
						return true;
					}
				} else if (homeNumber == 0) {
					p.sendMessage(api.getLangString("noHome"));
					return true;
				} else if (homeNumber > 1) {
					String text = "";
					for (String home : SetHome.homes.getConfigurationSection(p.getName()).getKeys(false)) {
						if (home.equals("homeNumber")) {
							home = "§b(" + api.getLangString("homeNumber") + homeNumber + ")§r";
						}
						text = text + home + ", ";
					}
					p.sendMessage("§6Homes: §r" + text);
					return true;
				} else {
					api.incorrectSyntax(p, "/home <home>");
					return true;
				}
			} else if (args.length <= 1) {
				if (SetHome.homes.isSet(p.getName() + "." + args[0] + ".world")) {
					double x = SetHome.homes.getDouble(p.getName() + "." + args[0] + ".x");
					double y = SetHome.homes.getDouble(p.getName() + "." + args[0] + ".y");
					double z = SetHome.homes.getDouble(p.getName() + "." + args[0] + ".z");
					double yaw = SetHome.homes.getDouble(p.getName() + "." + args[0] + ".yaw");
					double pitch = SetHome.homes.getDouble(p.getName() + "." + args[0] + ".pitch");
					String world = SetHome.homes.getString(p.getName() + "." + args[0] + ".world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z);
					loc.setYaw((float)yaw);
					loc.setPitch((float)pitch);
					api.tpDelayLoc(loc, p, plugin);
					return true;
				} else {
					p.sendMessage(api.getLangString("homeNotFound").replaceAll("%home%", args[0]));
					return true;
				}
			} else {
				api.incorrectSyntax(p, "/home <home>");
				return true;
			}
		}
	}

}
