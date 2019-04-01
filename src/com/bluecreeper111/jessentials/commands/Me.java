package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Me extends JCommand {

	public Me() {
		super("me", (plugin.getConfig().getString("permissionPrefix") + ".me"), false);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (args.length == 0) {
			api.incorrectSyntax(p, "/me <message>");
			return;
		} else {
			String text =  "";
			for (int i = 0; i < args.length; i++) {
				text = text + args[i] + " ";
			}
			Bukkit.broadcastMessage("§5 * " + p.getDisplayName() + "§5: " + text);
			return;
		}
	}

}
