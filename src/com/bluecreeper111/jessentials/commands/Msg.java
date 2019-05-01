package com.bluecreeper111.jessentials.commands;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Msg implements CommandExecutor {
	
	private Main plugin;
	
	public Msg(Main pl) {
		plugin = pl;
	}
	
	public static HashMap<String, Player> recentMessage = new HashMap<String, Player>();
	
	
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
			if (!p.hasPermission(api.perp() + ".msg")) {
				api.noPermission(p);
				return true;
			} else {
				if (!(args.length > 1)) {
					api.incorrectSyntax(p, "/" + label.toString() + " <player> <message>");
					return true;
				} else {
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						if (Mute.muted.contains(p.getName())) {
							p.sendMessage(api.getLangString("muted"));
							return true;
						}
						if (Main.playerData.getStringList(target.getName() + ".ignored-players").contains(p.getName())) {
							if (!p.hasPermission(api.perp() + ".ignore.bypass")) {
							p.sendMessage(playerIgnored.replaceAll("%player%", target.getName()));
							return true;
							}
							target.sendMessage(api.getLangString("ignoredBypass").replaceAll("%player%", p.getName()));
						}
						if (api.getPlayerData().getBoolean(target.getName() + ".afk") == true) {
							p.sendMessage(api.getLangString("afkAnswer"));
						}
						String msg =  "";
						for (int i = 1; i < args.length; i++) {
							msg = msg + args[i] + " ";
						}
						if (p.hasPermission(api.perp() + ".msg.color")) {
							msg = api.translateColor(msg);
						}
						if (Socialspy.socialSpying.size() > 0) {
							for (Player spy : Socialspy.socialSpying) {
								spy.sendMessage(api.getLangString("ssMessage").replaceAll("%message%", msgMessageSent.replaceAll("%player%", target.getDisplayName()).replaceAll("%message%", msg).replaceAll("me", p.getName())));
							}
						}
						target.sendMessage(msgMessageReceived.replaceAll("%player%", p.getDisplayName()).replaceAll("%message%", msg));
						recentMessage.put(target.getName(), p);
						p.sendMessage(msgMessageSent.replaceAll("%player%", target.getDisplayName()).replaceAll("%message%", msg));
						return true;
					}
				}
			}
		}
		
	}

}
