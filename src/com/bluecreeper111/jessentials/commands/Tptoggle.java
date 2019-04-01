package com.bluecreeper111.jessentials.commands;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;



public class Tptoggle implements CommandExecutor {
	
	public static HashSet<Player> tpToggled = new HashSet<Player>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else { 
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".tptoggle")) {
				api.noPermission(p);
				return true;
			} else {
				if (tpToggled.contains(p)) {
					p.sendMessage(api.getLangString("tptoggleOn"));
					tpToggled.remove(p);
					return true;
				}
				if (!tpToggled.contains(p)) {
					p.sendMessage(api.getLangString("tptoggleOff"));
					tpToggled.add(p);
					return true;
				}
			}
		}
		return true;
		
	}

}
