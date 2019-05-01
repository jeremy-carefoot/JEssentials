package com.bluecreeper111.jessentials.commands;

import java.sql.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class TempBan implements CommandExecutor {
	
	private Main plugin;
	
	public TempBan(Main pl) {
		plugin = pl;
	}
	
	public boolean isTimeInt(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public long whatTime(String args) {
	
    char d = args.charAt(args.length() - 1);
    String unit = Character.toString(d);
     if (unit.equals("h")) {
    	 long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return (time*60)*60;
    } else if(unit.equals("s")) {
    	long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return time;
    } else if (unit.equals("d")) {
    	long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return ((time*60)*60)*24;
    } else if (unit.equals("w")) {
    	long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return (((time*60)*60)*24)*7;
    } else if (unit.equals("m")) {
    	long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return time*60;
    } else if (args.substring(args.length() - 2).equals("mo")) {
    	long time2 = Long.parseLong(args.substring(0, args.length() - 2)) * 1L;
    	return ((((time2*60)*60)*24)*7)*4;
    } else if (unit.equals("y")) {
    	long time = Long.parseLong(args.substring(0, args.length() - 1)) * 1L;
    	return (((((time*60)*60)*24)*7)*4)*12;
    } else {
    	return 0L;
    }
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		BanList bans = Bukkit.getBanList(BanList.Type.NAME);
		String banBroadcast = api.getLangString("banBroadcast");
		boolean banEnable = plugin.getConfig().getBoolean("enable-broadcastBan");
		
		
			if (!(sender instanceof Player)) {
				if (!(args.length > 1)) {
					api.incorrectSyntaxConsole("/tempban <player> <time>");
					return true;
				} else {
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					}
					for (int i = 1; i < args.length; i++) {
						if ((!this.isTimeInt(args[i].substring(0, args[i].length() - 1)) && (!this.isTimeInt(args[i].substring(0, args[i].length() - 2))))) {
							sender.sendMessage(api.getLangString("invalidArgumentNumber"));
							return true;
						}
					}
					long temptime = 0L;
					for (int i = 1; i < args.length; i++) {
						temptime = temptime + this.whatTime(args[i]);
					}
					if (temptime == 0L) {
						sender.sendMessage(api.getLangString("tempbanTimes"));
						sender.sendMessage("s (seconds), m (minutes), h (hours), d (days), w (weeks), mo (months)");
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
					
					bans.addBan(target.getName(), "§6You have been tempbanned by: " + "§rConsole", new Date(System.currentTimeMillis()+temptime*1000), "Console");
					try {
					target.kickPlayer(bans.getBanEntry(target.getName()).getReason());
					} catch (NullPointerException e) {
						sender.sendMessage("§4NullPointerException. Was the date you gave too high?");
						return true;
					}
					if (banEnable == true) {
						Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", "Console").replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', bans.getBanEntry(target.getName()).getReason())));
						return true;
					} else {
						sender.sendMessage(api.getLangString("banMessage").replaceAll("%player%", target.getName()));
						return true;
					}
					
				}
			} else {
				Player p = (Player) sender;
				if (!p.hasPermission(api.perp() + ".tempban")) {
					api.noPermission(p);
					return true;
				} else {
					if(!(args.length > 2)) {
						api.incorrectSyntax(p, "/tempban <player> <time>");
					}
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					}
					for (int i = 1; i < args.length; i++) {
						if (!this.isTimeInt(args[i])) {
							p.sendMessage(api.getLangString("invalidArgumentNumber"));
							return true;
						}
					}
					long temptime = 0L;
					for (int i = 1; i < args.length; i++) {
						temptime = temptime + this.whatTime(args[i]);
					}
					if (temptime == 0L) {
						p.sendMessage(api.getLangString("tempbanTimes"));
						p.sendMessage("s (seconds), m (minutes), h (hours), d (days), w (weeks), mo (months)");
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
					Date date =  new Date(System.currentTimeMillis()+temptime*1000);
							
					bans.addBan(target.getName(), "§6You have been tempbanned by: §r" + p.getName(), date, p.getName());
					try {
						target.kickPlayer(bans.getBanEntry(target.getName()).getReason());
						} catch (NullPointerException e) {
							p.sendMessage("§4NullPointerException. Was the date you gave too high?");
							return true;
						}
					if (banEnable == true) {
						Bukkit.broadcastMessage(banBroadcast.replaceAll("%player%", target.getName()).replaceAll("%banner%", p.getName().toString()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', bans.getBanEntry(target.getName()).getReason())));
						return true;
					} else {
						p.sendMessage(api.getLangString("banMessage").replaceAll("%player%", target.getName()));
						return true;
					}
					
				}
				}
			}
	}


