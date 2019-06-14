package com.bluecreeper111.jessentials.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.jessentials.api.api;

public class MailTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1) {
			list.add("send");
			list.add("clear");
			list.add("read");
			if (sender.hasPermission(api.perp() + ".mail.sendall")) {
				list.add("sendall");
			}
			return list;
		}
		return null;
	}

}
