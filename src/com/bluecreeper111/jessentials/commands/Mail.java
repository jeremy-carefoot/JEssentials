package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Mail extends JCommand implements Listener {

	public Mail() {
		super("mail", (plugin.getConfig().getString("permissionPrefix") + ".mail"), false);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String loginNone = api.getLangString("mailNone");
		String playerIgnored = api.getLangString("ignored");
		Player p = (Player) sender;
		api.loadPlayerData();
		if ((args.length > 2) && args[0].equalsIgnoreCase("send")) {
			if (!p.hasPermission(api.perp() + ".mail.send")) {
				api.noPermission(p);
				return;
			} else {
				OfflinePlayer target = api.getOfflinePlayer(args[1]);
				if (target == null) {
					api.pNotFound(p, args[1]);
					return;
				}
				if (Mute.muted.contains(p.getName())) {
					p.sendMessage(api.getLangString("muted"));
					return;
				}
				if (api.getPlayerData().getStringList(target.getName() + ".ignored-players").contains(p.getName())) {
					if (!p.hasPermission(api.perp() + ".ignore.bypass")) {
						p.sendMessage(playerIgnored.replaceAll("%player%", target.getName()));
						return;
					} 
				}
				String text = "";
				for (int i = 2; i < args.length; i++) {
					text = text + args[i] + " ";
				}
				if (Socialspy.socialSpying.size() > 0) {
					for (Player spy : Socialspy.socialSpying) {
						spy.sendMessage(api.getLangString("ssMail").replaceAll("%mail%", p.getName() + ": " + text));
					}
				}
				api.getPlayerData().set(target.getName() + ".mail." + p.getName(), text);
				api.savePlayerData();
				p.sendMessage(api.getLangString("mailSent"));
				return;
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("read")) {
			if (!api.getPlayerData().isSet(p.getName() + ".mail")) {
				p.sendMessage(loginNone.replaceAll("%player%", p.getName()));
				return;
			}
			String text = "";
			for (String player : api.getPlayerData().getConfigurationSection(p.getName() + ".mail").getKeys(false)) {
				String message = player + ": " + api.getPlayerData().getString(p.getName() + ".mail." + player);
				text = text + message + "\n";
			}
			p.sendMessage(api.getLangString("mailBox") + " \n§r" + text);
			return;
		} else if (args.length > 1 && args[0].equalsIgnoreCase("sendall")) {
			if (!p.hasPermission(api.perp() + ".mail.sendall")) {
				api.noPermission(p);
				return;
			} else {
				String text = "";
				for (int i = 1; i < args.length; i++) {
					text = text + args[i] + " ";
				}
				for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
					api.getPlayerData().set(player.getName() + ".mail." + p.getName(), text);
					api.savePlayerData();
				}
				p.sendMessage(api.getLangString("mailAll"));
				return;
			}
		} else if  (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
			api.getPlayerData().set(p.getName() + ".mail", null);
			api.savePlayerData();
			p.sendMessage(api.getLangString("mailClear"));
			return;
		} else {
			api.incorrectSyntax(p, "/mail [send:clear:sendall:read] [to] [message]");
			return;
		}
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String loginNone = api.getLangString("mailNone");
		String loginFull = api.getLangString("mailFull");
		Player p = e.getPlayer();
		if (!(api.getPlayerData().isSet(p.getName() + ".mail"))) {
			p.sendMessage(loginNone.replaceAll("%player%", p.getName()));
		} else {
		  int messages = api.getPlayerData().getConfigurationSection(p.getName() + ".mail").getKeys(false).size();
		  p.sendMessage(loginFull.replaceAll("%player%", p.getName()).replaceAll("%messages%", Integer.toString(messages)));
		}
	}

}
