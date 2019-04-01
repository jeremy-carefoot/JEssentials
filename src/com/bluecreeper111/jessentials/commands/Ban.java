package com.bluecreeper111.jessentials.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Ban implements CommandExecutor {
	
	private Main plugin;
	
	public Ban(Main pl) {
		plugin = pl;
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BanList bans = Bukkit.getBanList(BanList.Type.NAME);
		String banBroadcast = api.getLangString("banBroadcast");
		boolean banEnable = plugin.getConfig().getBoolean("enable-broadcastBan");
		
		
			if (!(sender instanceof Player)) {
				if (!(args.length > 1)) {
					api.incorrectSyntaxConsole("/ban <player> [reason]");
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} 
					if (bans.isBanned(target.getName())) {
						sender.sendMessage(api.getLangString("alreadyBanned"));
						return true;
					}
					if (target.hasPermission(api.perp() + ".ban.exempt")) {
						sender.sendMessage(api.getLangString("banExempt"));
						return true;
					}
					String text = "";
					for (int i = 1; i < args.length; i++) {
						text = text + args[i] + " ";
					}
					bans.addBan(target.getName(), ChatColor.translateAlternateColorCodes('&', (text)), null, "Console");
					target.kickPlayer(ChatColor.translateAlternateColorCodes('&', text));
					if (banEnable == true) {
						Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", "Console").replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', text)));
						return true;
					} else {
						sender.sendMessage(api.getLangString("banMessage"));
						return true;
					}
					
				}
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".ban")) {
					api.noPermission(p);
					return true;
				} else {
					if (!(args.length > 1)) {
						api.incorrectSyntax(p, "/ban <player> [reason]");
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} 
						if (bans.isBanned(target.getName())) {
							p.sendMessage(api.getLangString("alreadyBanned"));
							return true;
						}
						if (target.hasPermission(api.perp() + ".ban.exempt")) {
							p.sendMessage(api.getLangString("banExempt"));
							return true;
						}
						String text = "";
						for (int i = 1; i < args.length; i++) {
							text = text + args[i] + " ";
						}
						bans.addBan(target.getName(), ChatColor.translateAlternateColorCodes('&', (text)), null, p.getName());
						target.kickPlayer(ChatColor.translateAlternateColorCodes('&', text));
						if (banEnable == true) {
							Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", p.getName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', text)));
							return true;
						} else {
							p.sendMessage(api.getLangString("banMessage"));
							return true;
						}
					}
				}
			}
	}

}
