package com.bluecreeper111.jessentials.commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class Socialspy extends JCommand {

	public Socialspy() {
		super("socialspy", (plugin.getConfig().getString("permissionPrefix") + ".socialspy"), false);
	}
	
	public static HashSet<Player> socialSpying = new HashSet<Player>();

	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		String socialSpy = api.getLangString("ssEnable");
		String socialSpyD = api.getLangString("ssDisable");
		Player p = (Player) sender;
		if (args.length == 0) {
			if (socialSpying.contains(p)) {
				socialSpying.remove(p);
				p.sendMessage(socialSpyD.replaceAll("%player%", p.getName()));
				return;
			} else {
				socialSpying.add(p);
				p.sendMessage(socialSpy.replaceAll("%player%", p.getName()));
				return;
			}
		} else if (args.length == 1) {
			if (!p.hasPermission(api.perp() + ".socialspy.others")) {
				api.noPermission(p);
				return;
			} else {
				@SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					api.pNotFound(p, args[0]);
					return;
				}
				if (socialSpying.contains(target)) {
					socialSpying.remove(target);
					target.sendMessage(socialSpyD.replaceAll("%player%", target.getName()));
					p.sendMessage(socialSpyD.replaceAll("%player%", target.getName()));
					return;
				} else {
					socialSpying.add(target);
					target.sendMessage(socialSpy.replaceAll("%player%", target.getName()));
					p.sendMessage(socialSpy.replaceAll("%player%", target.getName()));
					return;
				}
			}
		} else {
			api.incorrectSyntax(p, "/socialspy <player>");
			return;
		}
	}
	
	
	

}
