package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Sudo extends JCommand {

	public Sudo() {
		super("sudo", (plugin.getConfig().getString("permissionPrefix") + ".sudo"), true);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String sudo = api.getLangString("sudoMessage");
		if (!(sender instanceof Player)) {
			if (args.length > 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return;
				}
				String command = "";
				for (int i = 1; i < args.length; i++) {
					command = command + args[i] + " ";
				}
				if (command.startsWith("/")) {
				Bukkit.dispatchCommand(target, command.substring(1));
				sender.sendMessage(sudo.replaceAll("%player%", target.getDisplayName()));
				return;
				} else {
					if (target.hasPermission(api.perp() + ".chat.color")) {
						command = ChatColor.translateAlternateColorCodes('&', command);
					}
					target.chat(command);
					sender.sendMessage(sudo.replaceAll("%player%", target.getDisplayName()));
				}
			} else {
				api.incorrectSyntaxConsole("/sudo <player> [command]");
				return;
			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".sudo")) {
				api.noPermission(p);
				return;
			} else {
				if (!(args.length > 1)) {
					api.incorrectSyntax(p, "/sudo <player> [command]");
					return;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return;
					}
					if (target.hasPermission(api.perp() + ".sudo.exempt")) {
						p.sendMessage(api.getLangString("sudoExempt"));
						return;
					}
					String command = "";
					for (int i = 1; i < args.length; i++) {
						command = command + args[i] + " ";
					}
					if (command.startsWith("/")) {
					Bukkit.dispatchCommand(target, command.substring(1));
					p.sendMessage(sudo.replaceAll("%player%", target.getDisplayName()));
					return;
					} else {
						if (target.hasPermission(api.perp() + ".chat.color")) {
							command = ChatColor.translateAlternateColorCodes('&', command);
						}
						target.chat(command);
						p.sendMessage(sudo.replaceAll("%player%", target.getDisplayName()));
						return;
					}
				}
			}
			
		}
	}
	

}
