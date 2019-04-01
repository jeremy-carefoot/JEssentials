package com.bluecreeper111.jessentials.commands;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Ignore implements CommandExecutor {
	 
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".ignore")) {
				api.noPermission(p);
				return true;
			} else {
				try {
					Main.playerData.load(Main.playerDataFile);
				} catch (IOException | InvalidConfigurationException e) {
					e.printStackTrace();
				}
				if (args.length == 0) {
					api.incorrectSyntax(p, "/ignore <player>");
				} else {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						if (!Main.playerData.getStringList(p.getName() + ".ignored-players").contains(target.getName())) {
							List<String> list = Main.playerData.getStringList(p.getName() + ".ignored-players");
							list.add(target.getName());
							Main.playerData.set(p.getName() + ".ignored-players", list);
							try {
								Main.playerData.save(Main.playerDataFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(api.getLangString("ignoreMessage").replaceAll("%player%", target.getDisplayName()));
							return true;
						} else if (Main.playerData.getStringList(p.getName() + ".ignored-players").contains(target.getName())) {
							List<String> list = Main.playerData.getStringList(p.getName() + ".ignored-players");
							list.remove(target.getName());
							Main.playerData.set(p.getName() + ".ignored-players", list);
							try {
								Main.playerData.save(Main.playerDataFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(api.getLangString("ignoreMessageNo").replaceAll("%player%", target.getDisplayName()));
							return true;
						} else {
							return true;
						}
					}
				}
			}
		}
		return true;
	}

}
