package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Broadcast implements CommandExecutor {

	private Main plugin;

	public Broadcast(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String prefix = ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("broadcastPrefix") + "&r "));

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/broadcast <message>");
				return true;
			} else {
				String text = prefix;
				for (int a = 0; a < args.length; a++) {
					text = text + ChatColor.translateAlternateColorCodes('&', args[a]) + " ";
				}
				Bukkit.broadcastMessage(text);
				return true;

			}
		} else if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission(api.perp() + ".broadcast")) {
				api.noPermission(player);
				return true;
			} else {
				if (args.length == 0) {
					api.incorrectSyntax(player, "/broadcast <message>");
					return true;
				} else {
					String text = prefix;
					for (int i = 0; i < args.length; i++) {
						text = text + ChatColor.translateAlternateColorCodes('&', args[i]) + " ";
					}
					Bukkit.broadcastMessage(text);
					return true;
				}
			}
		}

		return true;
	}

}
