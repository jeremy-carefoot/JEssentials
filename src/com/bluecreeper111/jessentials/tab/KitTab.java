package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.commands.Kit;

public class KitTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission(api.perp() + ".kit")) return null;
			if (label.equalsIgnoreCase("kitoptions")) {
				if (args.length == 0) {
				if (sender.hasPermission(api.perp() + ".kit.firstjoin")) {
				list.add("firstjoin");
				}
				if (sender.hasPermission(api.perp() + ".kit.setdelay")) {
				list.add("delay");	
				}
				return list;
				}
			}
			if (label.equalsIgnoreCase("kit")) {
				if (args.length == 0) {
					for (String kit : Kit.kits.getConfigurationSection("Kit").getKeys(false)) {
						if (sender.hasPermission(api.perp() + ".kit." + kit) || sender.hasPermission(api.perp() + ".kit.*")) {
							list.add(kit);
						}
					}
					return list;
				}
			}
		return null;
	}

}
