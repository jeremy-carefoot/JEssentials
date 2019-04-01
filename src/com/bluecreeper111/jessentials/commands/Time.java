package com.bluecreeper111.jessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Time implements CommandExecutor {
	
	public boolean isInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".time")) {
				api.noPermission(p);
				return true;
			} else {
				if (label.equalsIgnoreCase("time")) {
					if (args.length != 1) {
						api.incorrectSyntax(p, "/time [night:day:time in ticks]");
						return true;
					} else {
						if (args[0].equalsIgnoreCase("day")) {
							p.getWorld().setTime(0L);
							p.sendMessage(api.getLangString("timeDay"));
							return true;
						} else if (args[0].equalsIgnoreCase("night")) {
							p.getWorld().setTime(14000L);
							p.sendMessage(api.getLangString("timeNight"));
							return true;
						} else if(this.isInt(args[0])) {
							Long ticks = (Integer.parseInt(args[0]) * 1L);
							p.getWorld().setTime(ticks);
							p.sendMessage(api.getLangString("timeTicks").replaceAll("%ticks%", Long.toString(ticks)));
							return true;
						} else {
							api.incorrectSyntax(p, "/time [night:day:time in ticks]");
							return true;
						}
					}
				} else if (label.equalsIgnoreCase("day")) {
					p.getWorld().setTime(0L);
					p.sendMessage(api.getLangString("timeDay"));
					return true;
				} else if (label.equalsIgnoreCase("night")) {
					p.getWorld().setTime(14000L);
					p.sendMessage(api.getLangString("timeNight"));
					return true;	
				} else {
					return true;
				}
			}
			
		}
	}

}
