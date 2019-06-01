package com.bluecreeper111.jessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JHelp implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((args.length == 0) || (args.length == 1 && (args[0].equalsIgnoreCase("1")))) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/heal §7- Heals the player.");
	        sender.sendMessage("§e/feed §7- Feeds the player.");
	        sender.sendMessage("§e/clear §7- Clears your inventory.");
	        sender.sendMessage("§e/fly §7- Enables/Disables flying.");
	        sender.sendMessage("§e/gm[c,s,a,sp] §7- Changes player's gamemode.");
	        sender.sendMessage("§8------------------§6Page 1/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("2")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/workbench §7- Opens workbench for the player.");
	        sender.sendMessage("§e/god §7- Enables/Disables god mode for player.");
	        sender.sendMessage("§e/enchant §7- Enchants item player is holding with specified enchant.");
	        sender.sendMessage("§e/je §7- Plugin info/Reload config");
	        sender.sendMessage("§e/broadcast §7- Publicly broadcast a message.");
	        sender.sendMessage("§8------------------§6Page 2/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("3")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/chatclear §7- Clear the chat.");
	        sender.sendMessage("§e/enderchest §7- Opens player's enderchest.");
	        sender.sendMessage("§e/motd §7- Allows modification of the server MOTD.");
	        sender.sendMessage("§e/exp §7- Gives player exp (in levels).");
	        sender.sendMessage("§e/sethome §7- Set a home.");
	        sender.sendMessage("§8------------------§6Page 3/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("4")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/delhome §7- Deletes a home.");
	        sender.sendMessage("§e/home §7- Teleport to a home.");
	        sender.sendMessage("§e/back §7- Teleports to death/previous teleport location.");
	        sender.sendMessage("§e/invsee §7- Allow modification/viewing of a players inventory.");
	        sender.sendMessage("§e/item §7- Gives an item based on Bukkit item id's.");
	        sender.sendMessage("§8------------------§6Page 4/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("5")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/kick §7- Kicks a player.");
	        sender.sendMessage("§e/kit §7- Ability to manipulate/get kits.");
	        sender.sendMessage("§e/msg §7- Messages a player.");
	        sender.sendMessage("§e/reply §7- Replys to a player's message");
	        sender.sendMessage("§e/mute §7- Mutes a player.");
	        sender.sendMessage("§8------------------§6Page 5/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("6")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/nick §7- Gives a custom nickname.");
	        sender.sendMessage("§e/realname §7- View a players IGN from nickname.");
	        sender.sendMessage("§e/getpos §7- Gets coordinates of a player.");
	        sender.sendMessage("§e/repair §7- Repairs item in hand, or whole inventory.");
	        sender.sendMessage("§e/setspawn §7- Sets the server spawn.");
	        sender.sendMessage("§8------------------§6Page 6/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("7")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/spawn §7- Teleports to server spawn.");
	        sender.sendMessage("§e/setwarp §7- Sets a warp.");
	        sender.sendMessage("§e/delwarp §7- Deletes a warp.");
	        sender.sendMessage("§e/warp §7- Teleports to a warp.");
	        sender.sendMessage("§e/warps §7- Lists warps.");
	        sender.sendMessage("§8------------------§6Page 7/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("8")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/jhelp - Opens help menu.");
	        sender.sendMessage("§e/setworldspawn §7- Sets the world spawnpoint.");
	        sender.sendMessage("§e/tpa §7- Request to teleport to a player.");
	        sender.sendMessage("§e/tpahere §7- Request a player to teleport to you.");
	        sender.sendMessage("§e/tpaccept §7- Accept a teleport request.");
	        sender.sendMessage("§8------------------§6Page 8/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("9")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/tpdeny §7- Reject a teleport request.");
	        sender.sendMessage("§e/tptoggle §7- Toggle teleportation to you.");
	        sender.sendMessage("§e/tp §7- Teleports to a player.");
	        sender.sendMessage("§e/tpo §7- Force teleports to a player.");
	        sender.sendMessage("§e/tppos §7- Teleport to direct coordinates.");
	        sender.sendMessage("§8------------------§6Page 9/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("10")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/kill §7- Kills a player.");
	        sender.sendMessage("§e/suicide §7- Commit suicide and broadcast it.");
	        sender.sendMessage("§e/time §7- Set the time in the world.");
	        sender.sendMessage("§e/ptime §7- Set the player's time.");
	        sender.sendMessage("§e/weather §7- Set the weather for the world.");
	        sender.sendMessage("§8------------------§6Page 10/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("11")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/vanish §7- Vanishes player.");
	        sender.sendMessage("§e/ignore §7- Ignores another player.");
	        sender.sendMessage("§e/jhelp §7- Plugin help.");
	        sender.sendMessage("§e/ban §7- Bans the specified player.");
	        sender.sendMessage("§e/unban §7- Unbans the specified player.");
	        sender.sendMessage("§8------------------§6Page 11/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("12")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/banip §7- Bans the specified player's ip address.");
	        sender.sendMessage("§e/tempban §7- Bans the specified player for a certain amount of time.");
	        sender.sendMessage("§e/afk §7- Enables/disables afk mode.");
	        sender.sendMessage("§e/list §7- Lists all online players.");
	        sender.sendMessage("§e/sudo §7- Put something in another players chat bar and run it.");
	        sender.sendMessage("§8------------------§6Page 12/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("13")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/balance §7- Displays the players balance.");
	        sender.sendMessage("§e/pay §7- Pays a player money. ");
	        sender.sendMessage("§e/baltop §7- Shows all top balances on the server.");
	        sender.sendMessage("§e/economy §7- Change any players balance.");
	        sender.sendMessage(" ");
	        sender.sendMessage("§8------------------§6Page 13/14§8------------------");
	        return true;
		} else if ((args.length == 1) && args[0].equalsIgnoreCase("14")) {
			sender.sendMessage("§8----------§6Just Essentials Help§8----------");
	        sender.sendMessage("§e/me §7- Broadcast you doing a simulated action.");
	        sender.sendMessage("§e/mail §7- Send, read, or clear mail.");
	        sender.sendMessage("§e/socialspy §7- View other players private messages.");
	        sender.sendMessage("§e/speed §7- Change your speed.");
	        sender.sendMessage("§e/seen §7- View when a player was last online.");
	        sender.sendMessage("§8------------------§6Page 14/14§8------------------");
	        return true;
		} else {
			sender.sendMessage("§c/jhelp <1-14>");
			return true;
		}
	}

}
