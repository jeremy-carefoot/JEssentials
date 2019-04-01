package com.bluecreeper111.jessentials.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class PTime implements CommandExecutor {
	
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
			if (!p.hasPermission(api.perp() + ".ptime")) {
				api.noPermission(p);
				return true;
			} else {
				if (label.equalsIgnoreCase("ptime")) {
					if (args.length != 1) {
						api.incorrectSyntax(p, "/ptime [night:day:time in ticks]");
						return true;
					} else {
						if (args[0].equalsIgnoreCase("day")) {
							p.setPlayerTime(0L, true);
							p.sendMessage(api.getLangString("ptimeDay"));
							return true;
						} else if (args[0].equalsIgnoreCase("night")) {
							p.setPlayerTime(14000L, true);
							p.sendMessage(api.getLangString("ptimeNight"));
							return true;
						} else if(this.isInt(args[0])) {
							Long ticks = (Integer.parseInt(args[0]) * 1L);
							p.setPlayerTime(ticks, true);;
							p.sendMessage(api.getLangString("ptimeTicks").replaceAll("%ticks%", Long.toString(ticks)));
							return true;
						} else {
							api.incorrectSyntax(p, "/ptime [night:day:time in ticks]");
							return true;
						}
					}
				} else if (label.equalsIgnoreCase("pday")) {
					p.setPlayerTime(0L, true);
					p.sendMessage(api.getLangString("ptimeDay"));
					return true;
				} else if (label.equalsIgnoreCase("pnight")) {
					p.setPlayerTime(14000L, true);
					p.sendMessage(api.getLangString("ptimeNight"));
					return true;	
				} else {
					return true;
				}
			}
			
		}
	}

}
