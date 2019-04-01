package com.bluecreeper111.jessentials.commands;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

import org.bukkit.ChatColor;

public class Warp implements CommandExecutor {
	
	private Main plugin;
	
	public Warp(Main pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
            SetWarp.warps.load(SetWarp.warpFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
		if (!SetWarp.warpFile.exists()) {
			try {
				SetWarp.warpFile.createNewFile();
				SetWarp.warps.save(SetWarp.warpFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (label.equalsIgnoreCase("warps")) {
				if (!SetWarp.warps.getConfigurationSection("Warps").getKeys(false).isEmpty() || !(SetWarp.warps.getConfigurationSection("Warps") == null)) {
					String text = "";
					for (String warps : SetWarp.warps.getConfigurationSection("Warps").getKeys(false)) {
						text = text + warps + ", ";
					}
					logger.info("Warps: " + text);
					return true;
				} else {
					logger.info(api.getLangString("noWarps"));
					return true;
				}
			}
			if (args.length != 2) {
				api.incorrectSyntaxConsole("/warp <player> <warp>");
				return true;
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (SetWarp.warps.isSet("Warps." + args[1] + ".world")) {
						double x = SetWarp.warps.getDouble("Warps." + args[1] + ".x");
						double y = SetWarp.warps.getDouble("Warps." + args[1] + ".y");
						double z = SetWarp.warps.getDouble("Warps." + args[1] + ".z");
						double yaw = SetWarp.warps.getDouble("Warps." + args[1] + ".yaw");
						double pitch = SetWarp.warps.getDouble("Warps." + args[1] + ".pitch");
						String world = SetWarp.warps.getString("Warps." + args[1] + ".world");
						Location loc = new Location(Bukkit.getWorld(world), x, y, z);
						loc.setPitch((float)pitch);
						loc.setYaw((float)yaw);
						target.teleport(loc);
						target.sendMessage(api.teleportMessage);
						logger.info(api.getLangString("playersTeleported"));
						return true;
						
					} else {
						logger.info(api.getLangString("warpNotFound"));
						return true;
					}
				}
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".warp")) {
				api.noPermission(p);
				return true;
			} else {
				if (label.equalsIgnoreCase("warps") || args.length == 0) {
					if (p.hasPermission(api.perp() + ".warp.list")) {
						if (!SetWarp.warps.getConfigurationSection("Warps").getKeys(false).isEmpty() || !(SetWarp.warps.getConfigurationSection("Warps") == null)) {
							String text = "";
							for (String warps : SetWarp.warps.getConfigurationSection("Warps").getKeys(false)) {
								text = text + warps + ", ";
							}
							p.sendMessage(ChatColor.GOLD + "Warps: " + ChatColor.RESET + text);
							return true;
						} else {
							p.sendMessage(api.getLangString("noWarps"));
							return true;
						}
					} else {
						api.noPermission(p);
						return true;
					}
				}
				if (args.length == 1) {
					if (SetWarp.warps.isSet("Warps." + args[0] + ".world")) {
						if (p.hasPermission(api.perp() + ".warp." + args[0]) || p.hasPermission(api.perp() + ".warp.*")) {
							double x = SetWarp.warps.getDouble("Warps." + args[0] + ".x");
							double y = SetWarp.warps.getDouble("Warps." + args[0] + ".y");
							double z = SetWarp.warps.getDouble("Warps." + args[0] + ".z");
							double yaw = SetWarp.warps.getDouble("Warps." + args[0] + ".yaw");
							double pitch = SetWarp.warps.getDouble("Warps." + args[0] + ".pitch");
							String world = SetWarp.warps.getString("Warps." + args[0] + ".world");
							Location loc = new Location(Bukkit.getWorld(world), x, y, z);
							loc.setPitch((float)pitch);
							loc.setYaw((float)yaw);
							api.tpDelayLoc(loc, p, plugin);
							return true;
						} else {
							api.noPermission(p);
							return true;
						}
					} else {
						p.sendMessage(api.getLangString("warpNotFound"));
						return true;
					}
				 
				} else if (args.length == 2) {
					if (!p.hasPermission(api.perp() + ".warp.others")) {
						api.noPermission(p);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} else {
							if (SetWarp.warps.isSet("Warps." + args[1] + ".world")) {
								double x = SetWarp.warps.getDouble("Warps." + args[1] + ".x");
								double y = SetWarp.warps.getDouble("Warps." + args[1] + ".y");
								double z = SetWarp.warps.getDouble("Warps." + args[1] + ".z");
								double yaw = SetWarp.warps.getDouble("Warps." + args[1] + ".yaw");
								double pitch = SetWarp.warps.getDouble("Warps." + args[1] + ".pitch");
								String world = SetWarp.warps.getString("Warps." + args[1] + ".world");
								Location loc = new Location(Bukkit.getWorld(world), x, y, z);
								loc.setPitch((float)pitch);
								loc.setYaw((float)yaw);
								api.tpDelayLoc(loc, target, plugin);
								p.sendMessage(api.getLangString("warpingPlayer").replaceAll("%warp%", args[1]));
								return true;
								
							} else {
								p.sendMessage(api.getLangString("warpNotFound"));
								return true;
							}
						}
					}
				} else {
					api.incorrectSyntax(p, "/warp <warp>");
					return true;
				}
			}
		}
	}

}
