package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class List extends JCommand {

	public List() {
		super("list", (plugin.getConfig().getString("permissionPrefix") + ".list"), false);
	}


	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		if (p.hasPermission(api.perp() + ".list.hidden")) {
			String text = "";
			String vanished = "";
			int vanishedPlayers = Vanish.vanishedPlayers.size();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Vanish.vanishedPlayers.contains(player)) { break; }
				text = text + player.getDisplayName() + "§r, ";
			}
			for (Player player : Vanish.vanishedPlayers) {
				vanished = vanished + player.getDisplayName() + "§r, ";
			}
			p.sendMessage(api.getLangString("listHeader").replaceAll("%players%", Integer.toString(Bukkit.getOnlinePlayers().size() - vanishedPlayers) + "§r\n" + text));
			if (Vanish.vanishedPlayers.size() > 0) {
				p.sendMessage(api.getLangString("listHidden").replaceAll("%hidden%", Integer.toString(vanishedPlayers)) + "§r\n" + vanished);
			}
			return;
			
		
		} else {
			String text = "";
			int vanishedPlayers = Vanish.vanishedPlayers.size();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Vanish.vanishedPlayers.contains(player)) { break; }
				text = text + player.getDisplayName() + "§r, ";
			}
			p.sendMessage(api.getLangString("listHeader").replaceAll("%players%", Integer.toString(Bukkit.getOnlinePlayers().size() - vanishedPlayers) + "§r\n" + text));
			return;
		}
		
		
		}
	

}
