package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Pay extends JCommand {

	public Pay() {
		super("pay", (plugin.getConfig().getString("permissionPrefix") + ".pay"), false);
	}
	private boolean isDouble(String number) {
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String payMessage = api.getLangString("payMessage");
		String payMessage2 = api.getLangString("receivePay");
		String display = Main.getEconomy().currencyNameSingular();
		Player p = (Player) sender;
		
		if (args.length != 2) {
			api.incorrectSyntax(p, "/pay <player> <amount>");
			return;
		} else {
			if (args[0].contains(",")) {
				if (p.hasPermission(api.perp() + ".pay.multiple")) {
					String[] payUsers = args[0].split(",");
					for (String user : payUsers) {
						Player play = Bukkit.getPlayerExact(user);
						if (play == null) {
							api.pNotFound(p, args[0]);
							return;
						}
					}
					if (this.isDouble(args[1])) {
						if (!Main.getEconomy().has(p, (Double.parseDouble(args[1]) * payUsers.length))) {
							p.sendMessage(api.getLangString("noMoney"));
							return;
						}
						for (String user : payUsers) {
							OfflinePlayer user1 = api.getOfflinePlayer(user);
							Main.getEconomy().depositPlayer(user1, Double.parseDouble(args[1]));
							Main.getEconomy().withdrawPlayer(p, Double.parseDouble(args[1]));
							p.sendMessage(payMessage.replaceAll("%amount%", args[1] + display).replaceAll("%player%", user1.getName()));
							user1.getPlayer().sendMessage(payMessage2.replaceAll("%amount%", args[1] + display).replaceAll("%player%", p.getName()));
						}
						return;
					} else {
						p.sendMessage(api.getLangString("invalidArgumentNumber"));
						return;
					}
				} else {
					api.noPermission(p);
					return;
				}
			}
			OfflinePlayer target = api.getOfflinePlayer(args[0]);
			if (target == null) {
				api.pNotFound(p, args[0]);
				return;
			}
			if (this.isDouble(args[1])) {
				double amount = Double.parseDouble(args[1]);
				if (!Main.getEconomy().has(p, amount)) {
					p.sendMessage(api.getLangString("noMoney"));
					return;
				}
				Main.getEconomy().depositPlayer(target, amount);
				Main.getEconomy().withdrawPlayer(p, amount);
				p.sendMessage(payMessage.replaceAll("%amount%", Double.toString(amount) + display).replaceAll("%player%", target.getName()));
				target.getPlayer().sendMessage(payMessage2.replaceAll("%amount%", Double.toString(amount) + display).replaceAll("%player%", p.getName()));
			} else {
				p.sendMessage(api.getLangString("invalidArgumentNumber"));
				return;
			}
			
		}
		
	}
	

}
