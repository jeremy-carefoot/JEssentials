package com.bluecreeper111.jessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Speed extends JCommand {

	public Speed() {
		super("speed", (plugin.getConfig().getString("permissionPrefix") + ".speed"), true);
	}

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length != 2) {
				api.incorrectSyntaxConsole("/speed <player> <speed>");
				return;
			} else {
				@SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFoundConsole(args[0]);
					return;
				}
				if (api.isInt(args[1])) {
					double speed = Integer.parseInt(args[1]);
					speed = speed / 10;
					if (speed > 1) {
						sender.sendMessage(api.getLangString("speedHigh"));
						return;
					}
					if (target.isFlying()) {
					target.setFlySpeed((float)speed);
					sender.sendMessage(api.getLangString("flySpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
					target.sendMessage(api.getLangString("flySpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
					return;
					} else {
					target.setWalkSpeed((float)speed);
					sender.sendMessage(api.getLangString("walkSpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
					target.sendMessage(api.getLangString("walkSpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
					return;
					}
				} else {
					api.incorrectSyntaxConsole("/speed <player> <speed>");
					return;
				}
			}
		} else {
			Player p = (Player) sender;
			if (args.length == 1) {
				if (api.isInt(args[0])) {
					double speed = Integer.parseInt(args[0]);
					speed = speed / 10;
					if (speed > 1) {
						p.sendMessage(api.getLangString("speedHigh"));
						return;
					}
					if (p.isFlying()) {
						if (!p.hasPermission(api.perp() + ".speed.fly")) {
							api.noPermission(p);
							return;
						} else {
							p.setFlySpeed((float)speed);
							p.sendMessage(api.getLangString("flySpeed").replaceAll("%player%", p.getDisplayName()).replaceAll("%speed%", args[0]));
							return;
						}
					} else {
						if (!p.hasPermission(api.perp() + ".speed.walk")) {
							api.noPermission(p);
							return;
						} else {
							p.setWalkSpeed((float)speed);
							p.sendMessage(api.getLangString("walkSpeed").replaceAll("%player%", p.getDisplayName()).replaceAll("%speed%", args[0]));
							return;
						}
					}
				} else {
					api.incorrectSyntax(p, "/speed <speed>");
					return;
				}
			} else if (args.length == 2) {
				if (!p.hasPermission(api.perp() + ".speed.others")) {
					api.noPermission(p);
					return;
				} else {
					@SuppressWarnings("deprecation")
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target == null) {
						api.pNotFound(p, args[0]);
						return;
					}
					if (api.isInt(args[1])) {
						double speed = Integer.parseInt(args[1]);
						speed = speed / 10;
						if (speed > 1) {
							p.sendMessage(api.getLangString("speedHigh"));
							return;
						}
						if (target.isFlying()) {
							target.setFlySpeed((float)speed);
							target.sendMessage(api.getLangString("flySpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
							p.sendMessage(api.getLangString("flySpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
							return;
						} else {
							target.setWalkSpeed((float)speed);
							target.sendMessage(api.getLangString("walkSpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
							p.sendMessage(api.getLangString("walkSpeed").replaceAll("%player%", target.getDisplayName()).replaceAll("%speed%", args[1]));
						}
					} else {
						api.incorrectSyntax(p, "/speed <player> <speed>");
						return;
					}
				}
			}
		}
		
	}

}
