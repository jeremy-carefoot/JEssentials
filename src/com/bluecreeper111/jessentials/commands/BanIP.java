package com.bluecreeper111.jessentials.commands;

import java.io.IOException;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class BanIP implements CommandExecutor {
	
	private Main plugin;
	
	public BanIP(Main pl) {
		plugin = pl;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BanList bans = Bukkit.getBanList(BanList.Type.IP);
		String banBroadcast = api.getLangString("ipBanBroadcast");
		boolean banEnable = plugin.getConfig().getBoolean("enable-broadcastIpBan");
		try {
			Main.playerData.load(Main.playerDataFile);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		
			if (!(sender instanceof Player)) {
				if (args.length != 1) {
					api.incorrectSyntaxConsole("/banip <player>");
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} 
					if (bans.isBanned(target.getAddress().getHostName())) {
						sender.sendMessage(api.getLangString("alreadyBanned"));
						return true;
					}
					if (target.hasPermission(api.perp() + ".ban.exempt")) {
						sender.sendMessage(api.getLangString("banExempt"));
						return true;
					}
					
					bans.addBan(target.getAddress().getHostName(), api.getLangString("ipBannedBy").replaceAll("%player%", "Console"), null, "Console").save();
					for (Player other : Bukkit.getOnlinePlayers()) {
						if (other.getAddress().getHostName().equals(target.getAddress().getHostName())) {
							other.kickPlayer(bans.getBanEntry(target.getAddress().getHostName()).getReason());
							Main.playerData.set(other.getName() + ".ip", target.getAddress().getHostName());
							try {
								Main.playerData.save(Main.playerDataFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					if (banEnable == true) {
						Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", "Console"));
						return true;
					} else {
						sender.sendMessage(api.getLangString("ipBanMessage").replaceAll("%player%", target.getName()));
						return true;
					}
					
				}
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".banip")) {
					api.noPermission(p);
					return true;
				} else {
					if (args.length != 1) {
						api.incorrectSyntax(p, "/ban <player>");
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(p, args[0]);
							return true;
						} 
						if (bans.isBanned(target.getAddress().getHostName())) {
							p.sendMessage(api.getLangString("alreadyBanned"));
							return true;
						}
						if (target.hasPermission(api.perp() + ".ban.exempt")) {
							p.sendMessage(api.getLangString("banExempt"));
							return true;
						}
						
						bans.addBan(target.getAddress().getHostName(), api.getLangString("ipBannedBy").replaceAll("%player%", p.getName()), null, p.getName()).save();
						for (Player other : Bukkit.getOnlinePlayers()) {
							if (other.getAddress().getHostName().equals(target.getAddress().getHostName())) {
								other.kickPlayer(bans.getBanEntry(target.getAddress().getHostName()).getReason());
								Main.playerData.set(other.getName() + ".ip", target.getAddress().getHostName());
								try {
									Main.playerData.save(Main.playerDataFile);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						if (banEnable == true) {
							Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", p.getName()));
							return true;
						} else {
							p.sendMessage(api.getLangString("ipBanMessage").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
	}

}
