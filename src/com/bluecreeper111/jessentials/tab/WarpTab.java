package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;
import com.bluecreeper111.jessentials.commands.SetWarp;

public class WarpTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		List<String> list = new ArrayList<String>();
		if (!(sender instanceof Player)) return null;
		if (args.length <= 1) {
			try {
				SetWarp.warps.getConfigurationSection("Warps");
			} catch(Exception e) {
				return null;
			}
			for (String warp : SetWarp.warps.getConfigurationSection("Warps").getKeys(false)) {
				if (sender.hasPermission(api.perp() + ".warp." + warp) || sender.hasPermission(api.perp() + ".warp.*")) {
					list.add(warp);
				}
			}
			return list;
		}
		return null;
	}

}
