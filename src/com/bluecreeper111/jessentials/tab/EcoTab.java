package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.jessentials.api.api;

public class EcoTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length <= 1) {
			if (!sender.hasPermission(api.perp() + ".economy")) return null;
			List<String> list = new ArrayList<String>();
			list.add("give");
			list.add("take");
			list.add("reset");
			return list;
		}
		return null;
	}

}
