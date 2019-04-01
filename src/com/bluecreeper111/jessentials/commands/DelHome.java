package com.bluecreeper111.jessentials.commands;


import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import org.bukkit.entity.Player;
import com.bluecreeper111.jessentials.api.api;

public class DelHome implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String delHomeMessage = api.getLangString("delHomeMessage");
		
		
		if (!(sender instanceof Player)) {
			api.notPlayer();
			return true;
		} else {
			Player p = (Player) sender;
			int houseNumber = SetHome.homes.getInt(p.getName() + ".homeNumber");
			if (!p.hasPermission(api.perp() + ".delhome")) {
				api.noPermission(p);
				return true;
			} 
			if (args.length == 0) {
				try {
	                SetHome.homes.load(SetHome.homesFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (InvalidConfigurationException e) {
	                e.printStackTrace();
	            }
				if (houseNumber == 1) {
					if (SetHome.homes.isSet(p.getName() + ".home.world")) {
						SetHome.homes.set(p.getName() + ".home", null);
						SetHome.homes.set(p.getName() + ".homeNumber", Integer.valueOf(houseNumber - 1));
						try {
							SetHome.homes.save(SetHome.homesFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(delHomeMessage.replaceAll("%player%", p.getName()));
						return true;
					} else {
						api.incorrectSyntax(p, "/delhome home");
						return true;
					}
	
				} else {
					api.incorrectSyntax(p, "/delhome <home>");
					return true;
				}
			} else if (args.length == 1) {
				try {
	                SetHome.homes.load(SetHome.homesFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (InvalidConfigurationException e) {
	                e.printStackTrace();
	            }
				if (SetHome.homes.isSet(p.getName() + "." + args[0] + ".world")) {
					SetHome.homes.set(p.getName() + "." + args[0], null);
					SetHome.homes.set(p.getName() + ".homeNumber", Integer.valueOf(houseNumber - 1));
					try {
						SetHome.homes.save(SetHome.homesFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					p.sendMessage(delHomeMessage.replaceAll("%player%", p.getName()));
					return true;
				} else {
					p.sendMessage(api.getLangString("homeNotFound").replaceAll("%home%", args[0]));
					return true;
				}
			} else {
				api.incorrectSyntax(p, "/delhome <home>");
				return true;
			}
		}
	}

}
