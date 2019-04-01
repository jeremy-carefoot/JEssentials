package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class Getpos implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".getpos")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length == 0) {
					double x = p.getLocation().getX();
					double y = p.getLocation().getY();
					double z = p.getLocation().getZ();
					p.sendMessage(api.getLangString("getPosMessage").replaceAll("%x%", Double.toString(Math.round(x))).replaceAll("%y%", Double.toString(Math.round(y))).replaceAll("%z%", Double.toString(Math.round(z))));
					return true;
				} else if(args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (!p.hasPermission(api.perp() + ".getpos.others")) {
						api.noPermission(p);
						return true;
					} else {
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} else {
							double x = target.getLocation().getX();
							double y = target.getLocation().getY();
							double z = target.getLocation().getZ();
							p.sendMessage(api.getLangString("getPosMessage").replaceAll("%x%", Double.toString(Math.round(x))).replaceAll("%y%", Double.toString(Math.round(y))).replaceAll("%z%", Double.toString(Math.round(z))));
							return true;
						}
					}
				} else {
					api.incorrectSyntax(p, "/getpos <player>");
					return true;
				}
			}
		}
	}

}
