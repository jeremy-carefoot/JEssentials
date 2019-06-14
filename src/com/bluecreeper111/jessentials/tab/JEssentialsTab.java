package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.jessentials.api.api;

public class JEssentialsTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender.hasPermission(api.perp() + ".reload") && arg3.length <= 1) {
			List<String> list = new ArrayList<>();
			list.add("reload");
			return list;
		}
		return null;
	}

}
