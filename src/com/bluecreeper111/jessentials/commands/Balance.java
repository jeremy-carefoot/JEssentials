package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Balance extends JCommand {

	public Balance() {
		super("balance", (plugin.getConfig().getString("permissionPrefix") + ".balance"), true);
	}
	public OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (Main.economyEnabled == false) {
			sender.sendMessage(api.getLangString("noEconomy"));
			return;
		}
		String display = Main.getEconomy().currencyNameSingular();
		if (!(sender instanceof Player)) {
			if (args.length != 1) {
				api.incorrectSyntaxConsole("/balance <player>");
				return;
			} else {
				OfflinePlayer target = this.getOfflinePlayer(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return;
				}
				double balance = Main.getEconomy().getBalance(target);
				sender.sendMessage(api.getLangString("balMessage").replaceAll("%player%", target.getName()).replaceAll("%amount%", Double.toString(balance) + display));
				return;
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				double balance  = Main.getEconomy().getBalance(p);
				p.sendMessage(api.getLangString("balMessage").replaceAll("%player%", p.getName()).replaceAll("%amount%", Double.toString(balance) + display));
				return;
			} else if (args.length == 1) {
				if (!p.hasPermission(api.perp() + ".balance.others")) {
					api.noPermission(p);
					return;
				} else {
					OfflinePlayer target = this.getOfflinePlayer(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return;
					}
					double balance = Main.getEconomy().getBalance(target);
					p.sendMessage(api.getLangString("balMessage").replaceAll("%player%", target.getName()).replaceAll("%amount%", Double.toString(balance) + display));
					return;
				}
			} else {
				api.incorrectSyntax(p, "/balance");
				return;
			}
		}
		
	}
	

}
