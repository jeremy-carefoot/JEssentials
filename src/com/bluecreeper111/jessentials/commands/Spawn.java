package com.bluecreeper111.jessentials.commands;


import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;



public class Spawn implements CommandExecutor {
	
	private Main plugin;
	
	public Spawn(Main pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/spawn <player>");
				return true;
			} else {
				@SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (!SetSpawn.spawn.isSet("Spawn.world")) {
						logger.info(api.getLangString("noSpawn"));
						return true;
			}
					double x = SetSpawn.spawn.getDouble("Spawn.x");
					double y = SetSpawn.spawn.getDouble("Spawn.y");
					double z = SetSpawn.spawn.getDouble("Spawn.z");
					double pitch = SetSpawn.spawn.getDouble("Spawn.pitch");
					double yaw = SetSpawn.spawn.getDouble("Spawn.yaw");
					String world = SetSpawn.spawn.getString("Spawn.world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z);
					loc.setPitch((float) pitch);
					loc.setYaw((float) yaw);
					target.teleport(loc);
					target.sendMessage(api.teleportMessage);
					logger.info("Player teleported.");
					return true;
				}
			}
		} else {
			Player p = (Player) sender;
			if (p.hasPermission(api.perp() + ".spawn")) {
				if (args.length == 0) {
					if (!SetSpawn.spawn.isSet("Spawn.world")) {
						p.sendMessage(api.getLangString("noSpawn"));
						return true;
					}
					double x = SetSpawn.spawn.getDouble("Spawn.x");
					double y = SetSpawn.spawn.getDouble("Spawn.y");
					double z = SetSpawn.spawn.getDouble("Spawn.z");
					double pitch = SetSpawn.spawn.getDouble("Spawn.pitch");
					double yaw = SetSpawn.spawn.getDouble("Spawn.yaw");
					String world = SetSpawn.spawn.getString("Spawn.world");
					Location loc = new Location(Bukkit.getWorld(world), x, y, z);
					loc.setPitch((float) pitch);
					loc.setYaw((float) yaw);
					api.tpDelayLoc(loc, p, plugin);
					return true;
				} else if (args.length == 1) {
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayerExact(args[0]);
					if (p.hasPermission(api.perp() + ".spawn.others")) {
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} else {
							if (!SetSpawn.spawn.isSet("Spawn.world")) {
								p.sendMessage(api.getLangString("noSpawn"));
								return true;
							}
							double x = SetSpawn.spawn.getDouble("Spawn.x");
							double y = SetSpawn.spawn.getDouble("Spawn.y");
							double z = SetSpawn.spawn.getDouble("Spawn.z");
							double pitch = SetSpawn.spawn.getDouble("Spawn.pitch");
							double yaw = SetSpawn.spawn.getDouble("Spawn.yaw");
							String world = SetSpawn.spawn.getString("Spawn.world");
							Location loc = new Location(Bukkit.getWorld(world), x, y, z);
							loc.setPitch((float) pitch);
							loc.setYaw((float) yaw);
							api.tpDelayLoc(loc, target, plugin);
							p.sendMessage(api.getLangString("spawnTeleporting"));
							return true;
						}
					} else {
						api.noPermission(p);
						return true;
					}
				} else {
					api.incorrectSyntax(p, "/spawn");
					return true;
				}
			} else {
				api.noPermission(p);
				return true;
			}
		}
	}

}
