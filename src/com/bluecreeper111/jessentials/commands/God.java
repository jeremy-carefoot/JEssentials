package com.bluecreeper111.jessentials.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.bluecreeper111.jessentials.api.api;

public class God implements CommandExecutor, Listener {

	private static List<Player> god = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Logger logger = Bukkit.getLogger();
		String godEnableMessage = api.getLangString("godEnable");
		String godDisableMessage = api.getLangString("godDisable");

		if (args.length < 1) {
			if (!(sender instanceof Player)) {
				api.notPlayer();
				return true;
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".god")) {
					api.noPermission(player);;
					return true;
				} else {
					if (!god.contains(player)) {
					god.add(player);
					player.sendMessage(godEnableMessage.replaceAll("%player%", player.getName().toString()));
					return true;
					} else {
						god.remove(player);
						player.sendMessage(godDisableMessage.replaceAll("%player%", player.getName().toString()));
						return true;
					}
				}
			}
		} else {
			if (!(sender instanceof Player)) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return true;
				} else {
					if (!god.contains(target)) {
						god.add(target);
						target.sendMessage(godEnableMessage.replaceAll("%player%", target.getName().toString()));
						logger.info(api.getLangString("godEnableSender").replaceAll("%player%", target.getName()));
						return true;
					} else {
						god.remove(target);
						target.sendMessage(godDisableMessage.replaceAll("%player%", target.getName().toString()));
						logger.info(api.getLangString("godDisableSender").replaceAll("%player%", target.getName()));
						return true;
					}
				}
			} else {
				Player player = (Player) sender;
				if (!player.hasPermission(api.perp() + ".god.others")) {
					api.noPermission(player);
					return true;
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(player, args[0]);
						return true;
					} else {
						if (!god.contains(target)) {
							god.add(target);
							target.sendMessage(godEnableMessage.replaceAll("%player%", target.getName().toString()));
							player.sendMessage(api.getLangString("godEnableSender").replaceAll("%player%", target.getName()));
							return true;
						} else {
							god.remove(target);
							target.sendMessage(godDisableMessage.replaceAll("%player%", target.getName().toString()));
							player.sendMessage(api.getLangString("godDisableSender").replaceAll("%player%", target.getName()));
							return true;
						}
					}
				}
			}
		}

	}
	@EventHandler
	public void ifGod(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player && god.contains(entity)) {
			Player player = (Player) event.getEntity();
			event.setCancelled(true);
			player.setHealth(20);
			player.setFoodLevel(20);
			
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		god.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		god.remove(event.getPlayer());
	}
}
