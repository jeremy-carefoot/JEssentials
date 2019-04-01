package com.bluecreeper111.jessentials.commands;


import java.util.HashSet;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Vanish implements CommandExecutor {
	
	private Main plugin;
	
	public Vanish(Main pl) {
		plugin = pl;
	}
	
	public static HashSet<Player> vanishedPlayers = new HashSet<Player>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".vanish")) {
				api.noPermission(p);
				return true;
			} else {
				if (vanishedPlayers.contains(p)) {
					for (Player players : plugin.getServer().getOnlinePlayers()) {
						players.showPlayer(plugin, p);
					}
					vanishedPlayers.remove(p);
					p.sendMessage(api.getLangString("vanishDisabled"));
					return true;
					} else if (!vanishedPlayers.contains(p)) {
						for (Player players : plugin.getServer().getOnlinePlayers()) {
							players.hidePlayer(plugin, p);
						}
						vanishedPlayers.add(p);
						p.sendMessage(api.getLangString("vanishEnabled"));
						return true;
					} else {
						return true;
					}
			}
			
		}
		return true;
	}

}
