package com.bluecreeper111.jessentials.commands;


import java.io.IOException;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Unban implements CommandExecutor {
	
	public OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BanList bans = Bukkit.getBanList(BanList.Type.NAME);
		BanList bans1 = Bukkit.getBanList(BanList.Type.IP);
		try {
			Main.playerData.load(Main.playerDataFile);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		if (!(sender instanceof Player)) {
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/unban <player>");
				return true;
			} else {
				OfflinePlayer target = this.getOfflinePlayer(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				}
				if (bans.isBanned(target.getName())) {
					bans.pardon(target.getName());
					sender.sendMessage(api.getLangString("unbanMessage").replaceAll("%player%", target.getName()));
					return true;
				} 
				for (BanEntry entry : bans1.getBanEntries()) {
					if (entry.getTarget().equals(Main.playerData.getString(target.getName() + ".ip"))) {
						bans1.pardon(entry.getTarget());
						sender.sendMessage(api.getLangString("unbanMessage").replaceAll("%player%", target.getName()));
						return true;
					}
				}
				
				sender.sendMessage(api.getLangString("notBanned"));
				return true;
				}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".unban")) {
				api.noPermission(p);
				return true;
			} else {
				if (args.length != 1) {
					api.incorrectSyntax(p, "/unban <player>");
					return true;
				} else {
					OfflinePlayer target = this.getOfflinePlayer(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					}
					if (bans.isBanned(target.getName())) {
						bans.pardon(target.getName());
						p.sendMessage(api.getLangString("unbanMessage").replaceAll("%player%", target.getName()));
						return true;
					} 
					for (BanEntry entry : bans1.getBanEntries()) {
						if (entry.getTarget().equals(Main.playerData.getString(target.getName() + ".ip"))) {
							bans1.pardon(entry.getTarget());
							sender.sendMessage(api.getLangString("unbanMessage").replaceAll("%player%", target.getName()));
							return true;
						}
					}
					p.sendMessage(api.getLangString("notBanned"));
					return true;
				}
			}
		}
	}

}
