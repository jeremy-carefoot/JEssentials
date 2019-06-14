package com.bluecreeper111.jessentials.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.api;

public class CreateCommand extends JCommand implements Listener {
	
	public HashMap<UUID, Integer> creatingcommands = new HashMap<>();
	public HashMap<UUID, ArrayList<String>> commandinfo = new HashMap<>();
	public HashMap<UUID, String> choosingaction = new HashMap<>();
	public static File commandsf = new File("plugins//JEssentials", "commands.yml");
	public static YamlConfiguration commands = YamlConfiguration.loadConfiguration(commandsf);
	
	public CreateCommand() {
		super("createcommand", (plugin.getConfig().getString("permissionPrefix") + ".createcommand"), false);
	}
	
	public static void saveCommands() {
		try {
			commands.save(commandsf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		creatingcommands.put(p.getUniqueId(), 1);
		p.sendMessage(" ");
		p.sendMessage("§6§lWelcome to the Command Creation Wizard\n§r§eAre you ready to create a command?\n§r§fType §ayes §fto proceed, or §ccancel §fto cancel.\n§r§7§o(Note: During the setup process, chat will be disabled)\n§r§7§o(§c/cancel §7can be used to cancel the process at any time)");
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		Player p = e.getPlayer();
		for (Iterator<Player> iterator = e.getRecipients().iterator(); iterator.hasNext();) {
			Player player = iterator.next();
			if (creatingcommands.containsKey(player.getUniqueId())) {
				iterator.remove();
			}
		}
		if (creatingcommands.containsKey(p.getUniqueId())) {
			e.setCancelled(true);
			int value = creatingcommands.get(p.getUniqueId());
			switch(value) {
			case 1:
				if (message.equalsIgnoreCase("cancel")) {
					p.sendMessage("§cCommand creation wizard cancelled.");
					creatingcommands.remove(p.getUniqueId());
					break;
				} else if (message.equalsIgnoreCase("yes")) {
					p.sendMessage(" ");
					p.sendMessage("§aBeginning command creation...\n§r§6Please type a name for your command. §o(Without the '/')");
					creatingcommands.put(p.getUniqueId(), 2);
					break;
				} else {
					p.sendMessage("§cInvalid response: Please reply with either 'yes' or 'cancel'.");
					break;
				}
			case 2:
				String[] name = message.split(" ");
				p.sendMessage(" ");
				p.sendMessage("§6Command name: §a" + name[0] + "§6.\n§6Your command will appear as: §a/" + name[0] + "§6.\n§r§6Please type a permission for your command.\n§e(jessentials.commands.§7§o<your permission here>§e)");
				commands.createSection("commands." + name[0]);
				saveCommands();
				creatingcommands.put(p.getUniqueId(), 3);
				ArrayList<String> put = new ArrayList<>();
				put.add(name[0]);
				commandinfo.put(p.getUniqueId(), put);
				break;
			case 3:
				String[] permission = message.split(" ");
				String commandname = commandinfo.get(p.getUniqueId()).get(0);
				p.sendMessage(" ");
				p.sendMessage("§6Command permission: §ejessentials.commands.§a" + permission[0] + "\n§r§6Opening command action manager in §e5 §6seconds.");
				commands.set("commands." + commandname + ".permission", permission[0]);
				commands.set("commands." + commandname + ".actions", new ArrayList<String>());
				saveCommands();
				ArrayList<String> put2 = commandinfo.get(p.getUniqueId());
				put2.add(permission[0]);
				commandinfo.put(p.getUniqueId(), put2);
				creatingcommands.put(p.getUniqueId(), 4);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						p.openInventory(getActionManager(p));
					}
				}, 100L);
				break;
			case 4:
				if (choosingaction.containsKey(p.getUniqueId()) && choosingaction.get(p.getUniqueId()).equals("message")) {
					String action =  "[message] " + ChatColor.translateAlternateColorCodes('&', e.getMessage());
					java.util.List<String> strings = commands.getStringList("commands." + commandinfo.get(p.getUniqueId()).get(0) + ".actions");
					strings.add(action);
					commands.set("commands." + commandinfo.get(e.getPlayer().getUniqueId()).get(0) + ".actions", strings);
					saveCommands();
					p.sendMessage("§aAction added.");
					choosingaction.remove(p.getUniqueId());
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.openInventory(getActionManager(p));
						}
					}, 20L);
					break;
				}
				if (choosingaction.containsKey(p.getUniqueId()) && choosingaction.get(p.getUniqueId()).equals("command")) {
					String command =  "[command] " + e.getMessage();
					java.util.List<String> strings = commands.getStringList("commands." + commandinfo.get(p.getUniqueId()).get(0) + ".actions");
					strings.add(command);
					commands.set("commands." + commandinfo.get(e.getPlayer().getUniqueId()).get(0) + ".actions", strings);
					saveCommands();
					p.sendMessage("§aAction added.");
					choosingaction.remove(p.getUniqueId());
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.openInventory(getActionManager(p));
						}
					}, 20L);
					break;
				}
				break;
			}
		}
	}
	
	public Inventory getActionManager(Player p) {
		Inventory gui = Bukkit.createInventory(null, 27, "§6" + p.getName() + "§e's Action Manager");
		ItemStack newaction = new ItemStack(Material.EMERALD, 1);
		ItemMeta actionmeta = newaction.getItemMeta();
		actionmeta.setDisplayName("§2§lAdd New Action");
		newaction.setItemMeta(actionmeta);
		gui.setItem(13, newaction);
		ItemStack complete = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta completemeta = complete.getItemMeta();
		completemeta.setDisplayName("§a§lComplete");
		complete.setItemMeta(completemeta);
		gui.setItem(26, complete);
		int actions = commands.getStringList("commands." + commandinfo.get(p.getUniqueId()).get(0) + ".actions").size();
		ItemStack currentactions = null;
		if (actions > 0) {
			currentactions = new ItemStack(Material.PAPER, actions);
		} else {
			currentactions = new ItemStack(Material.PAPER, 1);
		}
		ItemMeta meta = currentactions.getItemMeta();
		meta.setDisplayName("§8§lCurrent Actions: §6" + actions);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Your current existing actions:");
		int teleports = 0;
		int messages = 0;
		int cmds = 0;
		for (String action : commands.getStringList("commands." + commandinfo.get(p.getUniqueId()).get(0) + ".actions")) {
			if (action.startsWith("[teleport]")) {
				teleports = teleports + 1;
				continue;
			}
			if (action.startsWith("[message]")) {
				messages = messages + 1;
				continue;
			}
			if (action.startsWith("[command]")) {
				cmds = cmds + 1;
				continue;
			}
		}
		lore.add("§5Teleports: §f" + teleports);
		lore.add("§aMessages: §f" + messages);
		lore.add("§bCommands: §f" + cmds);
		meta.setLore(lore);
		currentactions.setItemMeta(meta);
		gui.setItem(18, currentactions);
		return gui;
	}
	@EventHandler
	public void interact(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().contains(p.getName()) && e.getView().getTitle().contains("Action Manager")) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);
			String name = e.getCurrentItem().getItemMeta().getDisplayName();
			switch(name) {
			case "§a§lComplete":
				p.sendMessage("§aYour command §2" + commandinfo.get(p.getUniqueId()).get(0) + "§a has been created.");
				creatingcommands.remove(p.getUniqueId());
				commandinfo.remove(p.getUniqueId());
				p.closeInventory();
				break;
			case "§2§lAdd New Action":
				Inventory gui = Bukkit.createInventory(null, 9, "§6§lChoose An Action");
				ItemStack message = new ItemStack(Material.PAPER, 1);
				ItemMeta meta = message.getItemMeta();
				meta.setDisplayName("§aSend a Message");
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§7Send a message to the player");
				lore.add("§7when they execute the command.");
				meta.setLore(lore);
				message.setItemMeta(meta);
				gui.setItem(4, message);
				ItemStack teleport = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta meta2 = teleport.getItemMeta();
				meta2.setDisplayName("§5Teleport Player");
				ArrayList<String> lore2 = new ArrayList<>();
				lore2.add("§7Teleport the player when");
				lore2.add("§7they execute the command.");
				meta2.setLore(lore2);
				teleport.setItemMeta(meta2);
				gui.setItem(5, teleport);
				ItemStack command = new ItemStack(Material.BEACON, 1);
				ItemMeta meta3 = command.getItemMeta();
				meta3.setDisplayName("§bExecute a Command");
				ArrayList<String> lore3 = new ArrayList<>();
				lore3.add("§7Execute an alternate command when");
				lore3.add("§7the player executes the command.");
				meta3.setLore(lore3);
				command.setItemMeta(meta3);
				gui.setItem(3, command);
				p.openInventory(gui);
				break;
			}
		}
		if (e.getView().getTitle().equals("§6§lChoose An Action")) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			e.setCancelled(true);
			String name = e.getCurrentItem().getItemMeta().getDisplayName();
			switch(name) {
			case "§aSend a Message":
				p.closeInventory();
				p.sendMessage(" ");
				p.sendMessage("§aPlease type a message to send to the player.\n§r§f§o(Use '&' for a color symbols)\n§r§f§o('%player%' will be replaced with the players name)");
				choosingaction.put(p.getUniqueId(), "message");
				break;
			case "§5Teleport Player":
				p.closeInventory();
				p.sendMessage(" ");
				p.sendMessage("§aPlease go to the location to teleport the player, and shift left-click.");
				choosingaction.put(p.getUniqueId(), "teleport");
				break;
			case "§bExecute a Command":
				p.closeInventory();
				p.sendMessage(" ");
				p.sendMessage("§aPlease type a command to execute. (Without the '/')\n§f§o(The command will be executed from console)\n§r§f§o('%player%' will be replaced with the players name)");
				choosingaction.put(p.getUniqueId(), "command");
				break;
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void shiftrightclick(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (!e.getPlayer().isSneaking()) return;
			if (choosingaction.containsKey(e.getPlayer().getUniqueId()) && choosingaction.get(e.getPlayer().getUniqueId()).equals("teleport")) {
				Location loc = e.getPlayer().getLocation();
				String teleport = "[teleport] " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " " + loc.getWorld().getName();
				java.util.List<String> strings = commands.getStringList("commands." + commandinfo.get(e.getPlayer().getUniqueId()).get(0) + ".actions");
				strings.add(teleport);
				commands.set("commands." + commandinfo.get(e.getPlayer().getUniqueId()).get(0) + ".actions", strings);
				saveCommands();
				e.getPlayer().sendMessage("§aAction added.");
				choosingaction.remove(e.getPlayer().getUniqueId());
				e.getPlayer().openInventory(getActionManager(e.getPlayer()));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void commandprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().startsWith("/cancel") && creatingcommands.containsKey(p.getUniqueId())) {
			e.setCancelled(true);
			p.sendMessage("§cCommand creation cancelled.");
			creatingcommands.remove(p.getUniqueId());
			if (commandinfo.containsKey(p.getUniqueId())) {
				commands.set("commands." + commandinfo.get(p.getUniqueId()).get(0), null);
				saveCommands();
				commandinfo.remove(p.getUniqueId());
			}
		}
		if (creatingcommands.containsKey(p.getUniqueId())) {
			e.setCancelled(true);
			p.sendMessage("§cCannot run commands while in command creation wizard. §r§7§o(Use /cancel to exit)");
			return;
		}
		for (String command : commands.getConfigurationSection("commands").getKeys(false)) {
			if (e.getMessage().startsWith("/" + command)) {
				e.setCancelled(true);
				if (p.hasPermission(api.perp() + ".commands." + commands.getString("commands." + command + ".permission"))) {
					for (String action : commands.getStringList("commands." + command + ".actions")) {
						if (action.startsWith("[message]")) {
							p.sendMessage((action.substring(10, action.length())).replaceAll("%player%", p.getName()));
						}
						if (action.startsWith("[command]")) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ((action.substring(10, action.length()).replaceAll("%player%", p.getName()))));
						}
						if (action.startsWith("[teleport]")) {
							String[] coords = action.split(" ");
							int x = Integer.parseInt(coords[1]);
							int y = Integer.parseInt(coords[2]);
							int z = Integer.parseInt(coords[3]);
							String world = coords[4];
							Location teleport = new Location(Bukkit.getWorld(world), x, y, z);
							p.teleport(teleport);
						}
					}
				} else {
					api.noPermission(p);
				}
				break;
			}
		}
	}
	
}