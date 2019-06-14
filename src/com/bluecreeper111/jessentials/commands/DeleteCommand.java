package com.bluecreeper111.jessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class DeleteCommand extends JCommand {
	
	public DeleteCommand() {
		super("deletecommand", (plugin.getConfig().getString("permissionPrefix") + ".deletecommand"), false);
	}

	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 1) {
			api.incorrectSyntax((Player)sender, "/deletecommand <command>");
			return;
		}
		Player p = (Player) sender;
		if (CreateCommand.commands.isSet("commands." + args[0])) {
			CreateCommand.commands.set("commands." + args[0], null);
			CreateCommand.saveCommands();
			p.sendMessage(api.getLangString("commandDeleted").replaceAll("%command%", "/" + args[0]));
			return;
		} else {
			p.sendMessage(api.getLangString("commandNotFound").replaceAll("%command%", args[0]));
			return;
		}
	}

}
