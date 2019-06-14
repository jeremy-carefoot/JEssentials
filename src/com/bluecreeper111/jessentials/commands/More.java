package com.bluecreeper111.jessentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class More extends JCommand {
	
	public More() {
		super("more", (plugin.getConfig().getString("permissionPrefix") + ".more"), false);
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
			p.sendMessage(api.getLangString("mustHoldItem"));
			return;
		}
		int max = p.getInventory().getMaxStackSize();
		p.getInventory().getItemInMainHand().setAmount(max);
		p.sendMessage(api.getLangString("toppedUp"));
		return;
	}

}
