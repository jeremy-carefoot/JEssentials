package com.bluecreeper111.jessentials.commands;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class DelWarp implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String delWarpMessage = api.getLangString("delWarpMessage");
		try {
            SetWarp.warps.load(SetWarp.warpFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/delwarp <warp>");
				return true;
			} else {
				if (!SetWarp.warps.isSet("Warps." + args[0] + ".world")) {
					logger.info(api.getLangString("warpNotFound").replaceAll("%warp%", args[0]));
					return true;
				} else {
					SetWarp.warps.set("Warps." + args[0], null);
					try {
						SetWarp.warps.save(SetWarp.warpFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					logger.info(delWarpMessage.replaceAll("%player%", "console").replaceAll("%warp%", args[0]));
					return true;
				}
			}
		} else {
			Player p = (Player) sender;
			if (p.hasPermission(api.perp() + ".delwarp")) {
				if (args.length == 0) {
					api.incorrectSyntax(p, "/delwarp <warp>");
					return true;
				} else {
					if (SetWarp.warps.isSet("Warps." + args[0] + ".world")) {
						SetWarp.warps.set("Warps." + args[0], null);
						try {
							SetWarp.warps.save(SetWarp.warpFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(delWarpMessage.replaceAll("%player%", p.getName().toString()).replaceAll("%warp%", args[0]));
						return true;
					} else {
						p.sendMessage(api.getLangString("warpNotFound"));
						return true;
					}
				}
			} else {
				api.noPermission(p);
				return true;
			}
		}
		
	}

}
