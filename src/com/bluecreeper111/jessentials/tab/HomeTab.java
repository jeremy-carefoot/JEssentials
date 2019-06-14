package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.commands.SetHome;

public class HomeTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player)) return null;
		if (args.length <= 1) {
			int homeNumber = (SetHome.homes.isSet(sender.getName()) ? SetHome.homes.getConfigurationSection(sender.getName()).getKeys(false).size() : 0);
			if (homeNumber == 0 || homeNumber == 1) return null;
			if (homeNumber > 1) {
				List<String> list = new ArrayList<String>();
				for (String home : SetHome.homes.getConfigurationSection(sender.getName()).getKeys(false)) {
					list.add(home);
				}
				return list;
			}
		}
		return null;
	}

}
