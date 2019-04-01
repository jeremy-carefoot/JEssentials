package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Enderchest implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String enderchestMessage = api.getLangString("enderchestMessage");
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (!p.hasPermission(api.perp() + ".enderchest")) {
					api.noPermission(p);
					return true;
				} else {
					p.sendMessage(enderchestMessage.replaceAll("%player%", p.getName().toString()));
					p.openInventory(p.getEnderChest());
					return true;
				}
			} else if (args.length > 0) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (!p.hasPermission(api.perp() + ".enderchest.others")) {
					api.noPermission(p);
					return true;
				} else {
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						p.openInventory(target.getEnderChest());
						p.sendMessage(api.getLangString("enderchestOthers").replaceAll("%player%", target.getName()));
						return true;
					}
				}
			}
		}

		return true;
	}

}
