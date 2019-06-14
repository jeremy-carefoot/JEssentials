package com.bluecreeper111.jessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class ListCommand extends JCommand {
	
	public ListCommand() {
		super("listcommands", (plugin.getConfig().getString("permissionPrefix") + ".listcommands"), true);
	}

	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("§6§lCustom Commands:");
		if (CreateCommand.commands.getConfigurationSection("commands").getKeys(false).size() == 0) { 
			sender.sendMessage(api.getLangString("noCommands"));
			return;
		}
		for (String command : CreateCommand.commands.getConfigurationSection("commands").getKeys(false)) {
			sender.sendMessage("§a/" + command + " §e- Permission: §6" + api.perp() + ".commands." + CreateCommand.commands.getString("commands." + command + ".permission"));
		}
	}

}
