package com.bluecreeper111.jessentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.bluecreeper111.jessentials.api.JCommand;
import com.bluecreeper111.jessentials.api.JEconomy;
import com.bluecreeper111.jessentials.api.Language;
import com.bluecreeper111.jessentials.api.VaultHook;
import com.bluecreeper111.jessentials.api.teleportDelay;
import com.bluecreeper111.jessentials.api.teleportSafety;
import com.bluecreeper111.jessentials.commands.Afk;
import com.bluecreeper111.jessentials.commands.Back;
import com.bluecreeper111.jessentials.commands.Ban;
import com.bluecreeper111.jessentials.commands.BanIP;
import com.bluecreeper111.jessentials.commands.Broadcast;
import com.bluecreeper111.jessentials.commands.ChatClear;
import com.bluecreeper111.jessentials.commands.Clear;
import com.bluecreeper111.jessentials.commands.DelHome;
import com.bluecreeper111.jessentials.commands.DelWarp;
import com.bluecreeper111.jessentials.commands.Enchant;
import com.bluecreeper111.jessentials.commands.Enderchest;
import com.bluecreeper111.jessentials.commands.Exp;
import com.bluecreeper111.jessentials.commands.Feed;
import com.bluecreeper111.jessentials.commands.Fly;
import com.bluecreeper111.jessentials.commands.Gamemode;
import com.bluecreeper111.jessentials.commands.Getpos;
import com.bluecreeper111.jessentials.commands.God;
import com.bluecreeper111.jessentials.commands.Hat;
import com.bluecreeper111.jessentials.commands.Heal;
import com.bluecreeper111.jessentials.commands.Home;
import com.bluecreeper111.jessentials.commands.Ignore;
import com.bluecreeper111.jessentials.commands.Invsee;
import com.bluecreeper111.jessentials.commands.Item;
import com.bluecreeper111.jessentials.commands.JEssentials;
import com.bluecreeper111.jessentials.commands.JHelp;
import com.bluecreeper111.jessentials.commands.Kick;
import com.bluecreeper111.jessentials.commands.Kill;
import com.bluecreeper111.jessentials.commands.Kit;
import com.bluecreeper111.jessentials.commands.Mail;
import com.bluecreeper111.jessentials.commands.Motd;
import com.bluecreeper111.jessentials.commands.Msg;
import com.bluecreeper111.jessentials.commands.Mute;
import com.bluecreeper111.jessentials.commands.Nick;
import com.bluecreeper111.jessentials.commands.PTime;
import com.bluecreeper111.jessentials.commands.Realname;
import com.bluecreeper111.jessentials.commands.Repair;
import com.bluecreeper111.jessentials.commands.Reply;
import com.bluecreeper111.jessentials.commands.SetHome;
import com.bluecreeper111.jessentials.commands.SetSpawn;
import com.bluecreeper111.jessentials.commands.SetWarp;
import com.bluecreeper111.jessentials.commands.SetWorldSpawn;
import com.bluecreeper111.jessentials.commands.Spawn;
import com.bluecreeper111.jessentials.commands.TempBan;
import com.bluecreeper111.jessentials.commands.Time;
import com.bluecreeper111.jessentials.commands.Tp;
import com.bluecreeper111.jessentials.commands.Tpa;
import com.bluecreeper111.jessentials.commands.Tpo;
import com.bluecreeper111.jessentials.commands.Tppos;
import com.bluecreeper111.jessentials.commands.Tptoggle;
import com.bluecreeper111.jessentials.commands.Unban;
import com.bluecreeper111.jessentials.commands.Vanish;
import com.bluecreeper111.jessentials.commands.Warp;
import com.bluecreeper111.jessentials.commands.Weather;
import com.bluecreeper111.jessentials.commands.Workbench;
import com.bluecreeper111.jessentials.event.commandCooldown;
import com.bluecreeper111.jessentials.event.playerChat;
import com.bluecreeper111.jessentials.event.playerDeath;
import com.bluecreeper111.jessentials.event.playerGamemode;
import com.bluecreeper111.jessentials.event.playerGive;
import com.bluecreeper111.jessentials.event.playerJoinLeave;
import com.bluecreeper111.jessentials.signs.buySign;
import com.bluecreeper111.jessentials.signs.disposalSign;
import com.bluecreeper111.jessentials.signs.freeSign;
import com.bluecreeper111.jessentials.signs.healSign;
import com.bluecreeper111.jessentials.signs.kitSign;
import com.bluecreeper111.jessentials.signs.sellSign;
import com.bluecreeper111.jessentials.signs.warpSign;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static String noPermissionMessage;
	public static String notPlayerMessage;
	public static String playerNotFound;
	public static String incorrectSyntaxMessage;
	public static String permissionPrefix;
	public static Long tpDelay;
	public static boolean tpDelayEnable;
	public static boolean pApi;
	public static Long tpSafetyLength;
	public static String teleportMessage;
	public static BukkitScheduler scheduler;
	public static File playerDataFile = new File("plugins//JEssentials", "playerdata.yml");
	public static YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
	private static Chat chat = null;
	private static net.milkbowl.vault.permission.Permission permission = null;
	public JEconomy economyImplementer;
	private VaultHook vaulthook;
	private static Economy economy = null;
	public String checkedVersion = "";
	public double returnedVersion = 0.0D;
	public double currentVersion = 0.0D;
	public static boolean update;
	public static boolean economyEnabled;
	public static JavaPlugin plugin;
	public static File lang = new File("plugins//JEssentials", "lang.yml");
	public static YamlConfiguration language = YamlConfiguration.loadConfiguration(lang);

	public void onEnable() {
		Logger logger = Bukkit.getLogger();
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info("[JEssentials] -INFO- has been enabled successfuly.");
		logger.info("[JEssentials] -VERSION- Running version V." + pdfFile.getVersion());
		logger.info("[JEssentials] -INFO- Please submit all bugs to the github! Project is in early stages!");
		registerCommands();
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			economyImplementer = new JEconomy();
			vaulthook = new VaultHook(this);
			if (getConfig().getBoolean("enable-economy")) {
				vaulthook.hook();
			}
		} else {
			Bukkit.getConsoleSender().sendMessage("§c§l[JEssentials] -ERROR- Vault not found! Plugin may return errors if not installed.");
		}
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			getLogger().info("-INFO- PlaceholderAPI has been sucessfully hooked.");
			pApi = true;
		} else {
			getLogger().warning("-WARNING- PlaceholderAPI was not found. Features involving PlaceholderAPI will not correctly function.");
			pApi = false;
		}
		if (setupChat() && setupPermissions() && setupEconomy()) {
			logger.info("[JEssentials] Vault API loaded successfully!");
		} else {
			Bukkit.getConsoleSender().sendMessage("§c[JEssentials] -ERROR- Vault API failed to load.");
		}
		currentVersion = Double.valueOf(pdfFile.getVersion().replaceFirst("1.", "")).doubleValue();
		if (getConfig().getBoolean("checkForUpdates")) {
			logger.info("[JEssentials] Checking for new plugin updates...");
			returnedVersion = this.updateCheck(currentVersion);
			if (returnedVersion > currentVersion) {
				Bukkit.getConsoleSender().sendMessage("§e[JEssentials] -WARNING- There is a new version of the plugin available!");
				Bukkit.getConsoleSender().sendMessage("§e[JEssentials] -WARNING- New version can be found here:");
				Bukkit.getConsoleSender().sendMessage("§e[JEssentials] -WARNING- https://dev.bukkit.org/projects/just-essentials");
				update = true;
			} else if (returnedVersion == 0.0) {
				update = false;
			} else {
				logger.info("[JEssentials] -INFO- No new updates available!");
			}
		}
		JCommand.registerCommands(this);
		registerEvents();
		registerApiStrings();
		saveDefaultConfig();
		registerPermissions();
		loadMetrics();
	}

	public void onDisable() {
		Logger logger = Bukkit.getLogger();
		if (economyEnabled) {
			vaulthook.unhook();
		}
		logger.info("[JEssentials] -INFO- has been disabled with no errors.");

	}

	public void registerCommands() {
		getCommand("heal").setExecutor(new Heal());
		getCommand("feed").setExecutor(new Feed());
		getCommand("clear").setExecutor(new Clear());
		getCommand("fly").setExecutor(new Fly());
		getCommand("gmc").setExecutor(new Gamemode());
		getCommand("gms").setExecutor(new Gamemode());
		getCommand("gma").setExecutor(new Gamemode());
		getCommand("gmsp").setExecutor(new Gamemode());
		getCommand("workbench").setExecutor(new Workbench());
		getCommand("god").setExecutor(new God());
		getCommand("enchant").setExecutor(new Enchant());
		getCommand("jessentials").setExecutor(new JEssentials(this));
		getCommand("broadcast").setExecutor(new Broadcast(this));
		getCommand("chatclear").setExecutor(new ChatClear());
		getCommand("enderchest").setExecutor(new Enderchest());
		getCommand("motd").setExecutor(new Motd(this));
		getCommand("exp").setExecutor(new Exp());
		getCommand("sethome").setExecutor(new SetHome(this));
		getCommand("delhome").setExecutor(new DelHome());
		getCommand("home").setExecutor(new Home(this));
		getCommand("hat").setExecutor(new Hat());
		getCommand("back").setExecutor(new Back(this));
		getCommand("invsee").setExecutor(new Invsee());
		getCommand("item").setExecutor(new Item());
		getCommand("kick").setExecutor(new Kick());
		getCommand("kit").setExecutor(new Kit(this));
		getCommand("msg").setExecutor(new Msg(this));
		getCommand("reply").setExecutor(new Reply(this));
		getCommand("mute").setExecutor(new Mute(this));
		getCommand("nick").setExecutor(new Nick(this));
		getCommand("realname").setExecutor(new Realname());
		getCommand("getpos").setExecutor(new Getpos());
		getCommand("repair").setExecutor(new Repair());
		getCommand("setspawn").setExecutor(new SetSpawn());
		getCommand("spawn").setExecutor(new Spawn(this));
		getCommand("setwarp").setExecutor(new SetWarp());
		getCommand("delwarp").setExecutor(new DelWarp());
		getCommand("warp").setExecutor(new Warp(this));
		getCommand("setworldspawn").setExecutor(new SetWorldSpawn());
		getCommand("tpa").setExecutor(new Tpa(this));
		getCommand("tptoggle").setExecutor(new Tptoggle());
		getCommand("tp").setExecutor(new Tp(this));
		getCommand("tpo").setExecutor(new Tpo());
		getCommand("tppos").setExecutor(new Tppos());
		getCommand("kill").setExecutor(new Kill());
		getCommand("time").setExecutor(new Time());
		getCommand("ptime").setExecutor(new PTime());
		getCommand("weather").setExecutor(new Weather());
		getCommand("vanish").setExecutor(new Vanish(this));
		getCommand("ignore").setExecutor(new Ignore());
		getCommand("jhelp").setExecutor(new JHelp());
		getCommand("ban").setExecutor(new Ban(this));
		getCommand("unban").setExecutor(new Unban());
		getCommand("banip").setExecutor(new BanIP(this));
		getCommand("tempban").setExecutor(new TempBan(this));
		getCommand("afk").setExecutor(new Afk(this));
	}

	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new playerGamemode(), this);
		pm.registerEvents(new God(), this);
		pm.registerEvents(new playerGive(this), this);
		pm.registerEvents(new playerJoinLeave(this), this);
		pm.registerEvents(new teleportSafety(this), this);
		pm.registerEvents(new teleportDelay(), this);
		pm.registerEvents(new playerDeath(), this);
		pm.registerEvents(new playerChat(this), this);
		pm.registerEvents(new Kit(this), this);
		pm.registerEvents(new Afk(this), this);
		pm.registerEvents(new healSign(this), this);
		pm.registerEvents(new disposalSign(this), this);
		pm.registerEvents(new freeSign(this), this);
		pm.registerEvents(new warpSign(this), this);
		pm.registerEvents(new kitSign(this), this);
		pm.registerEvents(new buySign(this), this);
		pm.registerEvents(new sellSign(this), this);
		pm.registerEvents(new Mail(), this);
		pm.registerEvents(new commandCooldown(this), this);

	}

	public void registerPermissions() {
		PluginManager pm = Bukkit.getPluginManager();
		String prefix = getConfig().getString("permissionPrefix");
		pm.addPermission(new Permission(prefix + ".heal"));
		pm.addPermission(new Permission(prefix + ".heal.others"));
		pm.addPermission(new Permission(prefix + ".feed"));
		pm.addPermission(new Permission(prefix + ".feed.others"));
		pm.addPermission(new Permission(prefix + ".clear"));
		pm.addPermission(new Permission(prefix + ".clear.others"));
		pm.addPermission(new Permission(prefix + ".fly"));
		pm.addPermission(new Permission(prefix + ".fly.others"));
		pm.addPermission(new Permission(prefix + ".gmc"));
		pm.addPermission(new Permission(prefix + ".gmc.others"));
		pm.addPermission(new Permission(prefix + ".gms"));
		pm.addPermission(new Permission(prefix + ".gms.others"));
		pm.addPermission(new Permission(prefix + ".gmsp"));
		pm.addPermission(new Permission(prefix + ".gmsp.others"));
		pm.addPermission(new Permission(prefix + ".gma"));
		pm.addPermission(new Permission(prefix + ".gma.others"));
		pm.addPermission(new Permission(prefix + ".gamemode"));
		pm.addPermission(new Permission(prefix + ".workbench"));
		pm.addPermission(new Permission(prefix + ".god"));
		pm.addPermission(new Permission(prefix + ".god.others"));
		pm.addPermission(new Permission(prefix + ".enchant"));
		pm.addPermission(new Permission(prefix + ".enchant.*"));
		pm.addPermission(new Permission(prefix + ".enchant.protection"));
		pm.addPermission(new Permission(prefix + ".enchant.fire_protection"));
		pm.addPermission(new Permission(prefix + ".enchant.fall_protection"));
		pm.addPermission(new Permission(prefix + ".enchant.explosions_protection"));
		pm.addPermission(new Permission(prefix + ".enchant.projectile_protection"));
		pm.addPermission(new Permission(prefix + ".enchant.respiration"));
		pm.addPermission(new Permission(prefix + ".enchant.thorns"));
		pm.addPermission(new Permission(prefix + ".enchant.aqua_affinity"));
		pm.addPermission(new Permission(prefix + ".enchant.depth_strider"));
		pm.addPermission(new Permission(prefix + ".enchant.binding_curse"));
		pm.addPermission(new Permission(prefix + ".enchant.frost_walker"));
		pm.addPermission(new Permission(prefix + ".enchant.smite"));
		pm.addPermission(new Permission(prefix + ".enchant.sharpness"));
		pm.addPermission(new Permission(prefix + ".enchant.bane_of_arthropods"));
		pm.addPermission(new Permission(prefix + ".enchant.knockback"));
		pm.addPermission(new Permission(prefix + ".enchant.fire_aspect"));
		pm.addPermission(new Permission(prefix + ".enchant.looting"));
		pm.addPermission(new Permission(prefix + ".enchant.sweeping_edge"));
		pm.addPermission(new Permission(prefix + ".enchant.unbreaking"));
		pm.addPermission(new Permission(prefix + ".enchant.silk_touch"));
		pm.addPermission(new Permission(prefix + ".enchant.efficiency"));
		pm.addPermission(new Permission(prefix + ".enchant.power"));
		pm.addPermission(new Permission(prefix + ".enchant.fortune"));
		pm.addPermission(new Permission(prefix + ".enchant.flame"));
		pm.addPermission(new Permission(prefix + ".enchant.punch"));
		pm.addPermission(new Permission(prefix + ".enchant.infinity"));
		pm.addPermission(new Permission(prefix + ".enchant.luck_of_the_sea"));
		pm.addPermission(new Permission(prefix + ".enchant.mending"));
		pm.addPermission(new Permission(prefix + ".enchant.lure"));
		pm.addPermission(new Permission(prefix + ".enchant.vanishing_curse"));
		pm.addPermission(new Permission(prefix + ".give"));
		pm.addPermission(new Permission(prefix + ".info"));
		pm.addPermission(new Permission(prefix + ".broadcast"));
		pm.addPermission(new Permission(prefix + ".chatclear"));
		pm.addPermission(new Permission(prefix + ".chatclear.others"));
		pm.addPermission(new Permission(prefix + ".reload"));
		pm.addPermission(new Permission(prefix + ".enderchest"));
		pm.addPermission(new Permission(prefix + ".enderchest.others"));
		pm.addPermission(new Permission(prefix + ".motd"));
		pm.addPermission(new Permission(prefix + ".motd.set"));
		pm.addPermission(new Permission(prefix + ".motd.enable"));
		pm.addPermission(new Permission(prefix + ".exp"));
		pm.addPermission(new Permission(prefix + ".exp.others"));
		pm.addPermission(new Permission(prefix + ".sethome"));
		pm.addPermission(new Permission(prefix + ".sethome.multiple"));
		pm.addPermission(new Permission(prefix + ".delhome"));
		pm.addPermission(new Permission(prefix + ".home"));
		pm.addPermission(new Permission(prefix + ".hat"));
		pm.addPermission(new Permission(prefix + ".back"));
		pm.addPermission(new Permission(prefix + ".back.others"));
		pm.addPermission(new Permission(prefix + ".tpdelay.bypass"));
		pm.addPermission(new Permission(prefix + ".invsee"));
		pm.addPermission(new Permission(prefix + ".item"));
		pm.addPermission(new Permission(prefix + ".chat.color"));
		pm.addPermission(new Permission(prefix + ".kick"));
		pm.addPermission(new Permission(prefix + ".kick.exempt"));
		pm.addPermission(new Permission(prefix + ".kit"));
		pm.addPermission(new Permission(prefix + ".kit.*"));
		pm.addPermission(new Permission(prefix + ".kit.add"));
		pm.addPermission(new Permission(prefix + ".kit.delete"));
		pm.addPermission(new Permission(prefix + ".kit.firstjoin"));
		pm.addPermission(new Permission(prefix + ".msg"));
		pm.addPermission(new Permission(prefix + ".msg.color"));
		pm.addPermission(new Permission(prefix + ".reply"));
		pm.addPermission(new Permission(prefix + ".mute"));
		pm.addPermission(new Permission(prefix + ".mute.exempt"));
		pm.addPermission(new Permission(prefix + ".nick"));
		pm.addPermission(new Permission(prefix + ".nick.color"));
		pm.addPermission(new Permission(prefix + ".nick.others"));
		pm.addPermission(new Permission(prefix + ".realname"));
		pm.addPermission(new Permission(prefix + ".getpos"));
		pm.addPermission(new Permission(prefix + ".getpos.others"));
		pm.addPermission(new Permission(prefix + ".repair"));
		pm.addPermission(new Permission(prefix + ".repair.all"));
		pm.addPermission(new Permission(prefix + ".setspawn"));
		pm.addPermission(new Permission(prefix + ".spawn"));
		pm.addPermission(new Permission(prefix + ".spawn.others"));
		pm.addPermission(new Permission(prefix + ".setwarp"));
		pm.addPermission(new Permission(prefix + ".setwarp.overwrite"));
		pm.addPermission(new Permission(prefix + ".delwarp"));
		pm.addPermission(new Permission(prefix + ".warp"));
		pm.addPermission(new Permission(prefix + ".warp.others"));
		pm.addPermission(new Permission(prefix + ".warp.*"));
		pm.addPermission(new Permission(prefix + ".warp.list"));
		pm.addPermission(new Permission(prefix + ".setworldspawn"));
		pm.addPermission(new Permission(prefix + ".tpa"));
		pm.addPermission(new Permission(prefix + ".tpaccept"));
		pm.addPermission(new Permission(prefix + ".tpdeny"));
		pm.addPermission(new Permission(prefix + ".tpahere"));
		pm.addPermission(new Permission(prefix + ".tptoggle"));
		pm.addPermission(new Permission(prefix + ".tp"));
		pm.addPermission(new Permission(prefix + ".tp.others"));
		pm.addPermission(new Permission(prefix + ".tpo"));
		pm.addPermission(new Permission(prefix + ".tpo.others"));
		pm.addPermission(new Permission(prefix + ".tppos"));
		pm.addPermission(new Permission(prefix + ".kill"));
		pm.addPermission(new Permission(prefix + ".kill.others"));
		pm.addPermission(new Permission(prefix + ".suicide"));
		pm.addPermission(new Permission(prefix + ".time"));
		pm.addPermission(new Permission(prefix + ".ptime"));
		pm.addPermission(new Permission(prefix + ".weather"));
		pm.addPermission(new Permission(prefix + ".vanish"));
		pm.addPermission(new Permission(prefix + ".ignore"));
		pm.addPermission(new Permission(prefix + ".ignore.bypass"));
		pm.addPermission(new Permission(prefix + ".ban"));
		pm.addPermission(new Permission(prefix + ".ban.exempt"));
		pm.addPermission(new Permission(prefix + ".unban"));
		pm.addPermission(new Permission(prefix + ".banip"));
		pm.addPermission(new Permission(prefix + ".tempban"));
		pm.addPermission(new Permission(prefix + ".afk"));
		pm.addPermission(new Permission(prefix + ".afk.auto"));
		pm.addPermission(new Permission(prefix + ".afk.others"));
		pm.addPermission(new Permission(prefix + ".afk.kickexempt"));
		pm.addPermission(new Permission(prefix + ".list"));
		pm.addPermission(new Permission(prefix + ".list.hidden"));
		pm.addPermission(new Permission(prefix + ".sudo"));
		pm.addPermission(new Permission(prefix + ".sudo.exempt"));
		pm.addPermission(new Permission(prefix + ".sign.heal"));
		pm.addPermission(new Permission(prefix + ".sign.disposal"));
		pm.addPermission(new Permission(prefix + ".sign.free"));
		pm.addPermission(new Permission(prefix + ".sign.warp"));
		pm.addPermission(new Permission(prefix + ".sign.kit"));
		pm.addPermission(new Permission(prefix + ".sign.buy"));
		pm.addPermission(new Permission(prefix + ".balance"));
		pm.addPermission(new Permission(prefix + ".balance.others"));
		pm.addPermission(new Permission(prefix + ".pay"));
		pm.addPermission(new Permission(prefix + ".pay.multiple"));
		pm.addPermission(new Permission(prefix + ".baltop"));
		pm.addPermission(new Permission(prefix + ".me"));
		pm.addPermission(new Permission(prefix + ".economy"));
		pm.addPermission(new Permission(prefix + ".mail"));
		pm.addPermission(new Permission(prefix + ".mail.send"));
		pm.addPermission(new Permission(prefix + ".mail.sendall"));
		pm.addPermission(new Permission(prefix + ".socialspy"));
		pm.addPermission(new Permission(prefix + ".socialspy.others"));
		pm.addPermission(new Permission(prefix + ".speed"));
		pm.addPermission(new Permission(prefix + ".speed.walk"));
		pm.addPermission(new Permission(prefix + ".speed.fly"));
		pm.addPermission(new Permission(prefix + ".speed.others"));
		pm.addPermission(new Permission(prefix + ".cooldown.bypass"));
		pm.addPermission(new Permission(prefix + ".cooldown.bypass.*"));
		pm.addPermission(new Permission(prefix + ".kit.setdelay"));
		pm.addPermission(new Permission(prefix + ".kit.delay.bypass.*"));
		pm.addPermission(new Permission(prefix + ".seen"));
	}

	public void saveDefaultConfig() {
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveResource("config.yml", false);
		}
		if (!Main.playerDataFile.exists()) {
			try {
				Main.playerDataFile.createNewFile();
				Main.playerData.save(Main.playerDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (!lang.exists()) {
			try {
				lang.createNewFile();
				Language.addStrings();
				language.options().copyDefaults(true);
				language.save(lang);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (economyEnabled == false) return;
		if (!JEconomy.econ.exists()) {
			try {
				JEconomy.econ.createNewFile();
				JEconomy.economy.save(JEconomy.econ);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerApiStrings() {
		tpDelay = getConfig().getInt("tpDelay") * 20L;
		tpDelayEnable = getConfig().getBoolean("enable-tpDelay");
		scheduler = getServer().getScheduler();
		tpSafetyLength = getConfig().getInt("tpSafetyLength") * 20L;
		permissionPrefix = getConfig().getString("permissionPrefix");

	}

	public void loadMetrics() {
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("used_language", new Callable<String>() {
			@Override
			public String call() throws Exception {
				return getConfig().getString("language", "en");
			}
		}));
	}

	private boolean setupChat() {
		try {
			RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
			try {
				chat = rsp.getProvider();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§6[JEssentials] -WARNING- Permissions plugin not found. Some features may not work.");
				return true;
			}
			return true;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}

	private boolean setupPermissions() {
		try {
			RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> rsp = getServer().getServicesManager()
					.getRegistration(net.milkbowl.vault.permission.Permission.class);
			permission = rsp.getProvider();
			return true;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}

	public static Chat getChat() {
		return chat;
	}

	public static net.milkbowl.vault.permission.Permission getPermissions() {
		return permission;
	}

	public double updateCheck(double currentVersion) {
		try {
			URL url = new URL("https://servermods.forgesvc.net/servermods/files?projectids=316204");
			URLConnection connect = url.openConnection();
			connect.setReadTimeout(5000);
			connect.setDoOutput(true);
			BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String respond = read.readLine();
			JSONArray array = (JSONArray) JSONValue.parse(respond);
			if (array.size() == 0) {
				Bukkit.getLogger().warning("[JEssentials] No files found, Could be an error with CurseForge API.");
				return currentVersion;
			}
			checkedVersion = ((String) ((JSONObject) array.get(array.size() - 1)).get("name"))
					.replace("JEssentials V", "").trim();
			return Double.valueOf(checkedVersion.replaceFirst("1.", "").trim().replace(" (Rebrand Update)", ""))
					.doubleValue();

		} catch (Exception e) {
			Bukkit.getLogger().severe("[JEssentials] Failed to check for a new update.");
			return 0.0;
		}

	}

	public boolean setupEconomy() {
		try {
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			if (rsp == null) {
				Bukkit.getConsoleSender().sendMessage("§6[JEssentials] -WARNING- No economy plugin found. Some features will not work!");
				economyEnabled = false;
				return true;
			}
			economy = rsp.getProvider();
			economyEnabled = true;
			return true;
		} catch (NoClassDefFoundError e) {
			economyEnabled = false;
			return false;
		}
	}

	public static Economy getEconomy() {
		return economy;
	}

}
