package com.bluecreeper111.jessentials.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.api;

public class Gamemode implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Logger logger = Bukkit.getLogger();

		if (cmd.getName().equalsIgnoreCase("gmc")) {
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					api.notPlayer();
					return true;
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gmc")) {
						api.noPermission(player);
						return true;
					} else {
						player.setGameMode(GameMode.CREATIVE);
						player.sendMessage(api.getLangString("gmcMessage"));
						return true;

					}
				}
			} else {
				if (!(sender instanceof Player)) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} else {
						target.setGameMode(GameMode.CREATIVE);
						target.sendMessage(api.getLangString("gmcMessage"));
						logger.info(api.getLangString("gmcMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gmc.others")) {
						api.noPermission(player);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(player, args[0]);
							return true;
						} else {
							target.setGameMode(GameMode.CREATIVE);
							target.sendMessage(api.getLangString("gmcMessage"));
							player.sendMessage(api.getLangString("gmcMessageSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("gms")) {
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					api.notPlayer();
					return true;
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gms")) {
						api.noPermission(player);
						return true;
					} else {
						player.setGameMode(GameMode.SURVIVAL);
						player.sendMessage(api.getLangString("gmsMessage"));
						return true;

					}
				}
			} else {
				if (!(sender instanceof Player)) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} else {
						target.setGameMode(GameMode.SURVIVAL);
						target.sendMessage(api.getLangString("gmsMessage"));
						logger.info(api.getLangString("gmsMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gms.others")) {
						api.noPermission(player);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(player, args[0]);
							return true;
						} else {
							target.setGameMode(GameMode.SURVIVAL);
							target.sendMessage(api.getLangString("gmsMessage"));
							player.sendMessage(api.getLangString("gmsMessageSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("gma")) {
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					api.notPlayer();
					return true;
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gma")) {
						api.noPermission(player);
						return true;
					} else {
						player.setGameMode(GameMode.ADVENTURE);
						player.sendMessage(api.getLangString("gmaMessage"));
						return true;

					}
				}
			} else {
				if (!(sender instanceof Player)) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} else {
						target.setGameMode(GameMode.ADVENTURE);
						target.sendMessage(api.getLangString("gmaMessage"));
						logger.info(api.getLangString("gmaMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gma.others")) {
						api.noPermission(player);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.noPermission(player);
							return true;
						} else {
							target.setGameMode(GameMode.ADVENTURE);
							target.sendMessage(api.getLangString("gmaMessage"));
							player.sendMessage(api.getLangString("gmaMessageSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					api.notPlayer();
					return true;
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gmsp")) {
						api.noPermission(player);
						return true;
					} else {
						player.setGameMode(GameMode.SPECTATOR);
						player.sendMessage(api.getLangString("gmspMessage"));
						return true;

					}
				}
			} else {
				if (!(sender instanceof Player)) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFoundConsole(args[0]);
						return true;
					} else {
						target.setGameMode(GameMode.SPECTATOR);
						target.sendMessage(api.getLangString("gmspMessage"));
						logger.info(api.getLangString("gmspMessageSender").replaceAll("%player%", target.getName()));
						return true;
					}
				} else {
					Player player = (Player) sender;
					if (!player.hasPermission(api.perp() + ".gmsp.others")) {
						api.noPermission(player);
						return true;
					} else {
						Player target = Bukkit.getPlayerExact(args[0]);
						if (target == null) {
							api.pNotFound(player, args[0]);
							return true;
						} else {
							target.setGameMode(GameMode.SPECTATOR);
							target.sendMessage(api.getLangString("gmspMessage"));
							player.sendMessage(api.getLangString("gmspMessageSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}
	
		return true;
	}

}
