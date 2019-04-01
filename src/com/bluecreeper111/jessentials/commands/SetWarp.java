package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class SetWarp implements CommandExecutor {
	
	public static File warpFile = new File("plugins/JEssentials", "warps.yml");
	public static YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String warpMessage = api.getLangString("setWarp");
		if (!warpFile.exists()) {
			try {
				warpFile.createNewFile();
				warps.save(warpFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".setwarp")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					api.incorrectSyntax(p, "/setwarp <warp name>");
					return true;
				} else if (args.length == 1) {
					if (warps.isSet("Warps." + args[0] + ".world") && !p.hasPermission(api.perp() + ".setwarp.overwrite")) {
						p.sendMessage(api.getLangString("warpSet"));
						return true;
					}
					if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("others") || args[0].equalsIgnoreCase("*")) {
						p.sendMessage(api.getLangString("invalidWarpName"));
						return true;
					}
					double x = p.getLocation().getX();
					double y = p.getLocation().getY();
					double z = p.getLocation().getZ();
					double pitch = p.getLocation().getPitch();
					double yaw = p.getLocation().getYaw();
					String world = p.getWorld().getName();
					warps.set("Warps." + args[0] + ".world", world);
					warps.set("Warps." + args[0] + ".x", x);
					warps.set("Warps." + args[0] + ".y", y);
					warps.set("Warps." + args[0] + ".z", z);
					warps.set("Warps." + args[0] + ".pitch", pitch);
					warps.set("Warps." + args[0] + ".yaw", yaw);
					try {
						warps.save(warpFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					p.sendMessage(warpMessage.replaceAll("%player%", p.getName().toString()).replaceAll("%warp%", args[0]));
					return true;
				} else {
					api.incorrectSyntax(p, "/setwarp <warp name>");
					return true;
				}
			}
		}
	}

}
