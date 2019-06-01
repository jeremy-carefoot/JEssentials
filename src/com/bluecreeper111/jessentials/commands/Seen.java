package com.bluecreeper111.jessentials.commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Seen extends JCommand {

	public Seen() {
		super("seen", (plugin.getConfig().getString("permissionPrefix") + ".seen"), true);
	}
	
	public String convertSeconds(Long seconds) {
		if (seconds < 60) {
			return (Long.toString(seconds) + " seconds");
		} else if (seconds >= 60 && seconds < 3600) {
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		} else if (seconds >= 3600 && seconds < 86400) {
			long hours = seconds / 3600;
			seconds = seconds - (hours * 3600);
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		} else if (seconds >= 86400 && seconds < 604800) {
			long days = seconds / 86400;
			seconds = seconds - (days * 86400);
			long hours = seconds / 3600;
			seconds = seconds - (hours * 3600);
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		} else if (seconds >= 604800 && seconds < 2592000) {
			long weeks = seconds / 604800;
			seconds = seconds - (weeks * 604800);
			long days = seconds / 86400;
			seconds = seconds - (days * 86400);
			long hours = seconds / 3600;
			seconds = seconds - (hours * 3600);
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(weeks) + " weeks, " + Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		} else if (seconds >= 2592000 && seconds < 31104000) {
			long months = seconds / 2592000;
			seconds = seconds - (months * 2592000);
			long weeks = seconds / 604800;
			seconds = seconds - (weeks * 604800);
			long days = seconds / 86400;
			seconds = seconds - (days * 86400);
			long hours = seconds / 3600;
			seconds = seconds - (hours * 3600);
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(months) + " months, " + Long.toString(weeks) + " weeks, " + Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		} else if (seconds >= 31104000) {
			long years = seconds / 31104000;
			seconds = seconds - (years * 31104000);
			long months = seconds / 2592000;
			seconds = seconds - (months * 2592000);
			long weeks = seconds / 604800;
			seconds = seconds - (weeks * 604800);
			long days = seconds / 86400;
			seconds = seconds - (days * 86400);
			long hours = seconds / 3600;
			seconds = seconds - (hours * 3600);
			long minutes = seconds / 60;
			seconds = seconds - (minutes * 60);
			return (Long.toString(years) + " years, " + Long.toString(months) + " months, " + Long.toString(weeks) + " weeks, " + Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes, " + Long.toString(seconds) + " seconds");
		}
		return "error";
	}
	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/seen <player>");
				return;
			}
			@SuppressWarnings("deprecation")
			OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
			if (player == null) {
				api.pNotFoundConsole(args[0]);
				return;
			}
			sender.sendMessage("§6§lPlayer §c" + player.getName() + "§6 is currently " + (player.isOnline() ? "§aonline" : "§coffline"));
			long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - player.getLastPlayed());
			sender.sendMessage("§6Last played: §c" + this.convertSeconds(seconds) + " §6ago");
		} else {
			Player p = (Player) sender;
			if (args.length != 1) {
				api.incorrectSyntax(p, "/seen <player>");
				return; 
			}
			@SuppressWarnings("deprecation")
			OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
			if (player == null) {
				api.pNotFoundConsole(args[0]);
				return;
			}
			p.sendMessage("§6Player §c" + player.getName() + "§6 is currently " + (player.isOnline() ? "§aonline" : "§coffline"));
			long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - player.getLastPlayed());
			if (player.getLastPlayed() == 0) {
				p.sendMessage("§4" + player.getName() + "§c has never played on this server!");
				return;
			}	
			if (!player.isOnline()) {
			p.sendMessage("§6Last played: §c" + this.convertSeconds(seconds) + " §6ago");
			}
		}
	}

}
