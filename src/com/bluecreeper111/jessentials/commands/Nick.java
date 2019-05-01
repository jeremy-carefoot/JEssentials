package com.bluecreeper111.jessentials.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.Main;
import com.bluecreeper111.jessentials.api.api;

public class Nick implements CommandExecutor {
	
	public static HashMap<String, Player> realname = new HashMap<String, Player>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String nickMessage = api.getLangString("nickMessage");
		if (!(sender instanceof Player)) {
			Logger logger = Bukkit.getLogger();
			if (args.length == 0) {
				api.incorrectSyntaxConsole("/nick <player> <nickname>");
				return true;
			} else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					String nick = args[1];
					nick = "§r" + nick + "§r~";
					nick = api.translateColor(nick);
					target.setDisplayName(nick);
					target.setCustomName(nick);
					target.setPlayerListName(nick);
					String nick1 = ChatColor.stripColor(nick);
					nick1 = nick1.replace("~", "");
					Main.playerData.set(target.getName() + ".nick", nick);
					Main.playerData.set(target.getName() + ".realnameKey", nick1);
					try {
						Main.playerData.save(Main.playerDataFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					realname.put(nick1, target);
					target.sendMessage(nickMessage.replaceAll("%player%", target.getName()).replaceAll("%nick%", nick));
					logger.info(api.getLangString("nickMessageSender").replaceAll("%player%", target.getName()));
					return true;
				}

			}
		} else {
			Player p = (Player) sender;
			if (!p.hasPermission(api.perp() + ".nick")) {
				api.noPermission(p);
				return true;
			} else {
				if (label.equalsIgnoreCase("nickreset")) {
					p.setDisplayName(p.getName());
					p.setCustomName(p.getName());
					p.setPlayerListName(p.getName());
					Main.playerData.set(p.getName() + ".nick", p.getName().toString());
					Main.playerData.set(p.getName() + ".realnameKey", p.getName());
					try {
						Main.playerData.save(Main.playerDataFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					realname.put(p.getName(), p);
					p.sendMessage(api.getLangString("nickReset"));
					return true;
				} else if (args.length == 0) {
					api.incorrectSyntax(p, "/nick <name>");
					return true;
				} else if (args.length == 1) {
					String nick = args[0];
					nick = "§r" + nick + "§r~";
					if (p.hasPermission(api.perp() + ".nick.color")) {
						nick = api.translateColor(nick);
					}
					p.setDisplayName(nick);
					p.setCustomName(nick);
					p.setPlayerListName(nick);
					String nick1 = ChatColor.stripColor(nick);
					nick1 = nick1.replace("~", "");
					Main.playerData.set(p.getName() + ".nick", nick);
					Main.playerData.set(p.getName() + ".realnameKey", nick1);
					try {
						Main.playerData.save(Main.playerDataFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					realname.put(nick1, p);
					p.sendMessage(nickMessage.replaceAll("%player%", p.getName()).replaceAll("%nick%", nick));
					return true;
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (!(target == null) && !p.hasPermission(api.perp() + ".nick.others")) {
						api.noPermission(p);
						return true;
					}
					if (target == null) {
						api.pNotFound(p, args[0]);
						return true;
					} else {
						String nick = args[1];
						nick = "§r" + nick + "§r~";
						if (p.hasPermission(api.perp() + ".nick.color")) {
							nick = api.translateColor(nick);
						}
							target.setDisplayName(nick);
							target.setCustomName(nick);
							target.setPlayerListName(nick);
							String nick1 = ChatColor.stripColor(nick);
							nick1 = nick1.replace("~", "");
							Main.playerData.set(target.getName() + ".nick", nick);
							Main.playerData.set(target.getName() + ".realnameKey", nick1);
							try {
								Main.playerData.save(Main.playerDataFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							realname.put(nick1, target);
							target.sendMessage(
									nickMessage.replaceAll("%player%", target.getName()).replaceAll("%nick%", nick));
							p.sendMessage(api.getLangString("nickMessageSender").replaceAll("%player%", target.getName()));
							return true;
						

					}
				} else {
					api.incorrectSyntax(p, "/nick <nickname>");
					return true;
				}
			}
		}
	}

}
