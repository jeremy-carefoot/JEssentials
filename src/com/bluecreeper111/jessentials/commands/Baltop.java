package com.bluecreeper111.jessentials.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Baltop extends JCommand {

	public Baltop() {
		super("baltop", (plugin.getConfig().getString("permissionPrefix") + ".baltop"), false);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String display = Main.getEconomy().currencyNameSingular();
		api.loadPlayerData();
		Player p = (Player) sender;
		HashMap<Double, String> manager = new HashMap<Double, String>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			manager.put(Main.getEconomy().getBalance(player), api.getPlayerData().getString(player.getName() + ".nick"));
		}
		ArrayList<Double> balances = new ArrayList<Double>(manager.keySet());
		Collections.sort(balances);
		Collections.reverse(balances);
		HashMap<Double, Integer> page = new HashMap<Double, Integer>();
		HashMap<Double, Integer> standing = new HashMap<Double, Integer>();
		int numbering = 1;
		int pageNumber = 1;
		for (Double balance : balances) {
			if (numbering % 5 == 0) {
				pageNumber++;
			}
			page.put(balance, pageNumber);
			standing.put(balance, numbering);
			numbering++;
		}
		if (args.length == 0) {
			String text = "";
			for (Double balance : balances) {
				if (page.get(balance) == 1) {
					text = text + standing.get(balance) + ". " + manager.get(balance) + ": " + Double.toString(balance) + display + "\n";
				}
			}
			p.sendMessage(api.getLangString("balTopHeader") + "§r\n" + text.replace("\\", "") + api.getLangString("balTopBottom").replaceAll("%page%", "1"));
			return;
			} else if (api.isInt(args[0])) {
				int argsPage = Integer.parseInt(args[0]);
				if (argsPage <= pageNumber) {
					String text = "";
					for (Double balance : balances) {
						if (page.get(balance) == argsPage) {
							text = text + standing.get(balance) + ". " + manager.get(balance) + ": " + Double.toString(balance) + display + "\n";
						}
					}
					p.sendMessage(api.getLangString("balTopHeader") + "§r\n" + text.replace("\\", "") + api.getLangString("balTopBottom").replaceAll("%page%", Integer.toString(argsPage)));
					return;
				} else {
					p.sendMessage(api.getLangString("balTopNoPage"));
					return;
				}
			 
			} else {
				api.incorrectSyntax(p, "/baltop <page>");
				return;
			}
	}
	
	
	

}
