package com.bluecreeper111.jessentials.commands;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.event.playerDeath;

public class Tppos implements CommandExecutor {
	
	public boolean ifInt(String number) {
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
			if (!p.hasPermission(api.perp() + ".tppos")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length != 3) {
					api.incorrectSyntax(p, "/tppos <x> <y> <z>");
					return true;
				} else {
					for (String arg : args) {
						if (this.ifInt(arg) == false) {
							api.incorrectSyntax(p, "/tppos <x> <y> <z>");
							return true;
						}
					}
					int x = Integer.parseInt(args[0]);
					int y = Integer.parseInt(args[1]);
					int z = Integer.parseInt(args[2]);
					Location loc = new Location(p.getWorld(), x, y, z);
					playerDeath.deathInfo.put(p.getName(), p.getLocation());
					p.teleport(loc);
					p.sendMessage(api.getLangString("teleportMessage"));
					return true;
				}
			}
		}
	}

}
