package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TimeTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(args.length <= 1)) return null;
		List<String> list = new ArrayList<String>();
		list.add("night");
		list.add("day");
		return list;
	}

}
