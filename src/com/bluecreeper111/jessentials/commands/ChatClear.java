package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class ChatClear implements CommandExecutor {


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String chatClear = api.getLangString("chatClear");

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				for (int i = 0; i < 60; i++) {
					Bukkit.broadcastMessage(" ");
				}
				Bukkit.broadcastMessage(chatClear);
				return true;
			} else if (args.length > 0) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				}
				for (int i = 0; i < 60; i++) {
					target.sendMessage(" ");
				}
				target.sendMessage(chatClear);
				return true;
			}
		} else if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (!p.hasPermission(api.perp() + ".chatclear")) {
					api.noPermission(p);
					return true;
				} else if (p.hasPermission(api.perp() + ".chatclear")) {
					for (int i = 0; i < 60; i++) {
						Bukkit.broadcastMessage(" ");
					}
					Bukkit.broadcastMessage(chatClear);
					return true;
				}
			} else if (args.length > 0) {
				if (p.hasPermission(api.perp() + ".chatclear.others")) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						for (int i = 0; i < 60; i++) {
							target.sendMessage(" ");
						}
						target.sendMessage(chatClear);
						return true;
					}
				} else {
					api.noPermission(p);
					return true;
				}
			}

		}

		return true;
	}

}
