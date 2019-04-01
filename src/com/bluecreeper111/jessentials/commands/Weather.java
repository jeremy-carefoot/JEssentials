package com.bluecreeper111.jessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Weather implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".weather")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					api.incorrectSyntax(p, "/weather [clear:rain:thunderstorm]");
					return true;
				} else {
					if (args[0].equalsIgnoreCase("clear")) {
						p.getWorld().setThundering(false);
						p.getWorld().setStorm(false);
						p.sendMessage(api.getLangString("weatherClear"));
						return true;
					}
					if (args[0].equalsIgnoreCase("rain")) {
						p.getWorld().setThundering(false);
						p.getWorld().setStorm(true);
						p.sendMessage(api.getLangString("weatherRain"));
						return true;
					}
					if (args[0].equalsIgnoreCase("thunderstorm")) {
						p.getWorld().setThundering(true);
						p.getWorld().setStorm(true);
						p.sendMessage(api.getLangString("weatherThunderstorm"));
						return true;
					}
					api.incorrectSyntax(p, "/weather [clear:rain:thunderstorm]");
					return true;
					
				}
			}
		}
	}

}
