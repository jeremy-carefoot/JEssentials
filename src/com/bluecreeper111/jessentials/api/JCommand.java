package com.bluecreeper111.jessentials.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.bluecreeper111.jessentials.commands.Balance;
import com.bluecreeper111.jessentials.commands.Baltop;
import com.bluecreeper111.jessentials.commands.Eco;
import com.bluecreeper111.jessentials.commands.List;
import com.bluecreeper111.jessentials.commands.Mail;
import com.bluecreeper111.jessentials.commands.Me;
import com.bluecreeper111.jessentials.commands.Pay;
import com.bluecreeper111.jessentials.commands.Socialspy;
import com.bluecreeper111.jessentials.commands.Speed;
import com.bluecreeper111.jessentials.commands.Sudo;

public abstract class JCommand implements CommandExecutor {
	
	private final String commandName;
	private final String permission;
	private final boolean consoleUse;
	public static JavaPlugin plugin;
	
	public JCommand(String commandName, String permission, boolean consoleUse) {
		this.commandName = commandName;
		this.permission = permission;
		this.consoleUse = consoleUse;
		plugin.getCommand(commandName).setExecutor(this);
	}
	public final static void registerCommands(JavaPlugin pl) {
		plugin = pl;
		new List();
		new Sudo();
		new Balance();
		new Pay();
		new Baltop();
		new Me();
		new Eco();
		new Mail();
		new Socialspy();
		new Speed();
	}
	
	public abstract void execute(CommandSender sender, Command cmd, String label, String[] args);
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase(commandName)) {
			return true;
		}
		if (!sender.hasPermission(permission)) {
			api.noPermission((Player)sender);
			return true;
		}
		if(!consoleUse && !(sender instanceof Player)) {
			api.notPlayer();
			return true;
		}
		execute(sender, cmd, label, args);
		return true;
		
	}
	

}
