package com.bluecreeper111.jessentials.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Eco extends JCommand {

	public Eco() {
		super("economy", (plugin.getConfig().getString("permissionPrefix") + ".economy"), true);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String display = Main.getEconomy().currencyNameSingular();
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/" + label + " [give:take:reset] <player> <amount>");
				return;
			} else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFoundConsole(args[1]);
					return;
				}
				double balance = Main.getEconomy().getBalance(target);
				Main.getEconomy().withdrawPlayer(target, balance);
				sender.sendMessage(api.getLangString("resetBalanceSender").replaceAll("%player%", target.getName()));
				return;
			} else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFoundConsole(args[1]);
					return;
				}
				if (api.isDouble(args[2])) {
					Main.getEconomy().depositPlayer(target, Double.parseDouble(args[2]));
					sender.sendMessage(api.getLangString("ecoGiveSender").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2] + display));
					return;
				} else {
					sender.sendMessage(api.getLangString("invalidArgumentNumber"));
					return;
				}
			} else if (args.length == 3 && args[0].equalsIgnoreCase("take")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFoundConsole(args[1]);
					return;
				}
				if (api.isDouble(args[2])) {
					if ((Main.getEconomy().getBalance(target) - Double.parseDouble(args[2]) < 0)) {
						sender.sendMessage(api.getLangString("ecoBelowZero"));
						return;
					}
					Main.getEconomy().withdrawPlayer(target, Double.parseDouble(args[2]));
					sender.sendMessage(api.getLangString("ecoTakeSender").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2] + display));
					return;
				} else {
					sender.sendMessage(api.getLangString("invalidArgumentNumber"));
					return;
				}
			} else {
				api.incorrectSyntaxConsole("/" + label + " [give:take:reset] <player> <amount>");
				return;
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				api.incorrectSyntax(p, "/" + label + " [give:take:reset] <player> <amount>");
				return;
			} else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFound(p, args[1]);
					return;
				}
				double balance = Main.getEconomy().getBalance(target);
				Main.getEconomy().withdrawPlayer(target, balance);
				sender.sendMessage(api.getLangString("resetBalanceSender").replaceAll("%player%", target.getName()));
				return;
			} else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFound(p, args[1]);
					return;
				}
				if (api.isDouble(args[2])) {
					Main.getEconomy().depositPlayer(target, Double.parseDouble(args[2]));
					p.sendMessage(api.getLangString("ecoGiveSender").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2] + display));
					return;
				} else {
					p.sendMessage(api.getLangString("invalidArgumentNumber"));
					return;
				}
			} else if (args.length == 3 && args[0].equalsIgnoreCase("take")) {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFound(p, args[1]);
					return;
				}
				if (api.isDouble(args[2])) {
					if ((Main.getEconomy().getBalance(target) - Double.parseDouble(args[2]) < 0)) {
						p.sendMessage(api.getLangString("ecoBelowZero"));
						return;
					}
					Main.getEconomy().withdrawPlayer(target, Double.parseDouble(args[2]));
					p.sendMessage(api.getLangString("ecoTakeSender").replaceAll("%player%", target.getName()).replaceAll("%amount%", args[2] + display));
					return;
				} else {
					p.sendMessage(api.getLangString("ecoTakeSender"));
					return;
				}
			} else {
				api.incorrectSyntax(p, "/" + label + " [give:take:reset] <player> <amount>");
				return;
			}
		}
		
	}

}
