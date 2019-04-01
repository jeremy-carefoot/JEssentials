package com.bluecreeper111.jessentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Reply implements CommandExecutor {
	
	private Main plugin;
	
	public Reply(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String msgMessageSent = api.translateColor(plugin.getConfig().getString("msgMessage-Sent"));
		String msgMessageReceived = api.translateColor(plugin.getConfig().getString("msgMessage-Receive"));
		String playerIgnored = api.getLangString("ignored"); 
		try {
			Main.playerData.load(Main.playerDataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".reply")) {
				api.noPermission(p);
				return true;
			} else {
				if (!(args.length > 0)) {
					api.incorrectSyntax(p, "/" + label.toString() + " <message>");
					return true;
				} else {
					String msg = "";
					for (int i = 0; i < args.length; i++) {
						msg = msg + args[i] + " ";
					}
					if (p.hasPermission(api.perp() + ".msg.color")) {
						msg = ChatColor.translateAlternateColorCodes('&', msg);
					}
					if (Msg.recentMessage.containsKey(p.getName())) {
						
						if (Mute.muted.contains(p.getName())) {
							p.sendMessage(api.getLangString("muted"));
							return true;
						}
						
						Player target = Msg.recentMessage.get(p.getName());
						
						if (Main.playerData.getStringList(target.getName() + ".ignored-players").contains(p.getName())) {
							if (!p.hasPermission(api.perp() + ".ignore.bypass")) {
							p.sendMessage(playerIgnored.replaceAll("%player%", target.getName()));
							return true;
							}
							target.sendMessage(api.getLangString("ignoredBypass").replaceAll("%player%", p.getName()));
						}
						if (Socialspy.socialSpying.size() > 0) {
							for (Player spy : Socialspy.socialSpying) {
								spy.sendMessage(api.getLangString("ssMessage").replaceAll("%msg%", msgMessageSent.replaceAll("%player%", target.getDisplayName()).replaceAll("%message%", msg).replaceAll("me", p.getName())));
							}
						}

						target.sendMessage(msgMessageReceived.replaceAll("%player%", p.getDisplayName()).replaceAll("%message%", msg));
						Msg.recentMessage.put(target.getName(), p);
						p.sendMessage(msgMessageSent.replaceAll("%player%", target.getDisplayName()).replaceAll("%message%", msg));
						return true;
					} else {
						p.sendMessage(api.getLangString("noMessages"));
						return true;
					}
				}
			}
		}
	}
}
