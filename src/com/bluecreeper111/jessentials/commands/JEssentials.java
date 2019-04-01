package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class JEssentials implements CommandExecutor {

	private Main plugin;

	public JEssentials(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Logger logger = Bukkit.getLogger();
		PluginDescriptionFile pdf = plugin.getDescription();

		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				logger.info("§6Just Essentials version V." + pdf.getVersion() + " developed by bluecreeper111");
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				plugin.reloadConfig();
				logger.info(api.getLangString("configReload"));
				return true;
			} else {
				api.incorrectSyntaxConsole("/jessentials [reload]");
				return true;
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (sender instanceof Player && p.hasPermission(api.perp() + ".info")) {
					p.sendMessage("§6Just Essentials version V." + pdf.getVersion() + " developed by bluecreeper111");
					return true;
				} else {
					api.noPermission(p);
					return true;
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (p.hasPermission(api.perp() + ".reload")) {
					plugin.reloadConfig();
					p.sendMessage(api.getLangString("configReload"));
					return true;
				} else {
					api.noPermission(p);
					return true;
				}
			} else {
				api.incorrectSyntax(p, "/jessentials [reload]");
			}
		}
		return true;
	}

}
