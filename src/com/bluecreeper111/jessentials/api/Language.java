package com.bluecreeper111.jessentials.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Language {
	
	public static File lang = new File("plugins//JEssentials", "lang.yml");
	public static YamlConfiguration language = YamlConfiguration.loadConfiguration(lang);
	
	private static boolean SetIfNoDefaultObject(YamlConfiguration config, String path) {
		if(config == null) return false;
		if(path == null) return false;
		
		if(config.getString("l." + path) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addStrings() {
		if(!SetIfNoDefaultObject(language, "noPermission")) {
			language.addDefault("l.noPermission", "&cYou do not have permission to do this!");
		}
		if(!SetIfNoDefaultObject(language, "pNotFound")) {
			language.addDefault("l.pNotFound", "&cPlayer %target% not found!");
		}
		if(!SetIfNoDefaultObject(language, "notPlayer")) {
			language.addDefault("l.notPlayer", "&cYou are not a player!");
		}
		if(!SetIfNoDefaultObject(language, "incorrectSyntax")) {
			language.addDefault("l.incorrectSyntax", "&cIncorrect syntax! Use %syntax%");
		}
		if(!SetIfNoDefaultObject(language, "teleportMessage")) {
			language.addDefault("l.teleportMessage", "&6Teleported!");
		}
		if(!SetIfNoDefaultObject(language, "tpDelayMessage")) {
			language.addDefault("l.tpDelayMessage", "&6Teleporting in &c%delay% &6seconds");
		}
		if(!SetIfNoDefaultObject(language, "teleportCancelled")) {
			language.addDefault("l.teleportCancelled", "&cTeleport cancelled because you moved!");
		}
		if(!SetIfNoDefaultObject(language, "notAfk")) {
			language.addDefault("l.notAfk", "&6You are no longer afk!");
		}
		if(!SetIfNoDefaultObject(language, "notAfkSender")) {
			language.addDefault("l.notAfkSender", "&6Player %player% &6is no longer afk!");
		}
		if(!SetIfNoDefaultObject(language, "Afk")) {
			language.addDefault("l.Afk", "&6You are now afk!");
		}
		if(!SetIfNoDefaultObject(language, "AfkSender")) {
			language.addDefault("l.AfkSender", "&6Player %player% &6is now afk!");
		}
		if(!SetIfNoDefaultObject(language, "afkKick")) {
			language.addDefault("l.afkKick", "Kicked due to inactivity.");
		}
		if(!SetIfNoDefaultObject(language, "afkBroadcast")) {
			language.addDefault("l.afkBroadcast", "&5 * Player %player% &5is now afk!");
		}
		if(!SetIfNoDefaultObject(language, "notAfkBroadcast")) {
			language.addDefault("l.notAfkBroadcast", "&5 * Player %player% &5is no longer afk!");
		}
		if(!SetIfNoDefaultObject(language, "backSender")) {
			language.addDefault("l.backSender", "&6Player %player% sent to their death location.");
		}
		if(!SetIfNoDefaultObject(language, "backNotDiedSender")) {
			language.addDefault("l.backNotDiedSender", "&cThat player hasn't died/teleported recently!");
		}
		if(!SetIfNoDefaultObject(language, "backNotDied")) {
			language.addDefault("l.backNotDied", "&cYou have not died/teleported recently!");
		}
		if(!SetIfNoDefaultObject(language, "noEconomy")) {
			language.addDefault("l.noEconomy", "&6&l[JEssentials] &r&cEconomy not enabled!");
		}
		if(!SetIfNoDefaultObject(language, "balMessage")) {
			language.addDefault("l.balMessage", "&aBalance of player &2%player% &ais %amount%");
		}
		if(!SetIfNoDefaultObject(language, "balTopHeader")) {
			language.addDefault("l.balTopHeader", "&aThe current baltop:");
		}
		if(!SetIfNoDefaultObject(language, "balTopBottom")) {
			language.addDefault("l.balTopBottom", "&2-Page %page%-");
		}
		if(!SetIfNoDefaultObject(language, "balTopNoPage")) {
			language.addDefault("l.balTopNoPage", "&cThat page doesn't exist!");
		}
		if(!SetIfNoDefaultObject(language, "banBroadcast")) {
			language.addDefault("l.banBroadcast", "&6Player &c%player% &6has been banned from the server for &r%reason%&6!");
		}
		if(!SetIfNoDefaultObject(language, "alreadyBanned")) {
			language.addDefault("l.alreadyBanned", "&cThat player is already banned!");
		}
		if(!SetIfNoDefaultObject(language, "banExempt")) {
			language.addDefault("l.banExempt", "&cThat player is exempted from being banned!");
		}
		if(!SetIfNoDefaultObject(language, "banMessage")) {
			language.addDefault("l.banMessage", "&6Player &c%player% &6has been banned.");
		}
		if(!SetIfNoDefaultObject(language, "ipBanBroadcast")) {
			language.addDefault("l.ipBanBroadcast", "&6Player &c%player% &6has been ip-banned from the server!");
		}
		if(!SetIfNoDefaultObject(language, "ipBannedBy")) {
			language.addDefault("l.ipBannedBy", "&6IP-Banned by: &r%player%");
		}
		if(!SetIfNoDefaultObject(language, "ipBanMessage")) {
			language.addDefault("l.ipBanMessage", "&6Player &c%player% &6has been ip-banned.");
		}
		if(!SetIfNoDefaultObject(language, "chatClear")) {
			language.addDefault("l.chatClear", "&2Chat has been cleared!");
		}
		if(!SetIfNoDefaultObject(language, "clearInvMessage")) {
			language.addDefault("l.clearInvMessage", "&6Your inventory has been cleared!");
		}
		if(!SetIfNoDefaultObject(language, "clearInvMessageSender")) {
			language.addDefault("l.clearInvMessageSender", "&6%player%'s inventory has been cleared.");
		}
		if(!SetIfNoDefaultObject(language, "delHomeMessage")) {
			language.addDefault("l.delHomeMessage", "&6Home deleted!");
		}
		if(!SetIfNoDefaultObject(language, "homeNotFound")) {
			language.addDefault("l.homeNotFound", "&cHome %home% not found!");
		}
		if(!SetIfNoDefaultObject(language, "delWarpMessage")) {
			language.addDefault("l.delWarpMessage", "&6%warp% deleted!");
		}
		if(!SetIfNoDefaultObject(language, "warpNotFound")) {
			language.addDefault("l.warpNotFound", "&c%warp% not found!");
		}
		if(!SetIfNoDefaultObject(language, "resetBalanceSender")) {
			language.addDefault("l.resetBalanceSender", "&aPlayer %player%'s balance has been reset.");
		}
		if(!SetIfNoDefaultObject(language, "ecoGiveSender")) {
			language.addDefault("l.ecoGiveSender", "&aPlayer %player% has been given %amount%");
		}
		if(!SetIfNoDefaultObject(language, "invalidArgumentNumber")) {
			language.addDefault("l.invalidArgumentNumber", "&cInvalid number given!");
		}
		if(!SetIfNoDefaultObject(language, "ecoBelowZero")) {
			language.addDefault("l.ecoBelowZero", "&cCannot do that! That would bring the player's balance below zero!");
		}
		if(!SetIfNoDefaultObject(language, "ecoTakeSender")) {
			language.addDefault("l.ecoTakeSender", "&aPlayer %player% has lost %amount%");
		}
		if(!SetIfNoDefaultObject(language, "enchantMessage")) {
			language.addDefault("l.enchantMessage", "&2Your item has been enchanted.");
		}
		if(!SetIfNoDefaultObject(language, "mustHoldItem")) {
			language.addDefault("l.mustHoldItem", "&cYou must hold an item!");
		}
		if(!SetIfNoDefaultObject(language, "enchantNotFound")) {
			language.addDefault("l.enchantNotFound", "&cThat enchantment was not found!");
		}
		if(!SetIfNoDefaultObject(language, "enchantOutOfBounds")) {
			language.addDefault("l.enchantOutOfBounds", "&cThat enchantment level is out of bounds! It can support enchants up to %max%.");
		}
		if(!SetIfNoDefaultObject(language, "enchantNotCompatible")) {
			language.addDefault("l.enchantNotCompatible", "&cThat item does not support that enchantment!");
		}
		if(!SetIfNoDefaultObject(language, "enderchestMessage")) {
			language.addDefault("l.enderchestMessage", "&5Enderchest opened!");
		}
		if(!SetIfNoDefaultObject(language, "enderchestOthers")) {
			language.addDefault("l.enderchestOthers", "&dOpened %player%'s enderchest!");
		}
		if(!SetIfNoDefaultObject(language, "expMessage")) {
			language.addDefault("l.expMessage", "&2You have been given &a%exp%&2!");
		}
		if(!SetIfNoDefaultObject(language, "expMessageSender")) {
			language.addDefault("l.expMessageSender", "&2Player %player% has been given &a%exp%&2 exp levels.");
		}
		if(!SetIfNoDefaultObject(language, "feedMessage")) {
			language.addDefault("l.feedMessage", "&6You have been fed.");
		}
		if(!SetIfNoDefaultObject(language, "feedMessageSender")) {
			language.addDefault("l.feedMessageSender", "&6Player %player% has been fed.");
		}
		if(!SetIfNoDefaultObject(language, "flyEnabled")) {
			language.addDefault("l.flyEnabled", "&6Fly has been enabled.");
		}
		if(!SetIfNoDefaultObject(language, "flyDisabled")) {
			language.addDefault("l.flyDisabled", "&6Fly has been disabled.");
		}
		if(!SetIfNoDefaultObject(language, "flyMessageSender")) {
			language.addDefault("l.flyMessageSender", "&6Fly toggled for %player%.");
		}
		if(!SetIfNoDefaultObject(language, "gmcMessage")) {
			language.addDefault("l.gmcMessage", "&2Gamemode switched to creative.");
		}
		if(!SetIfNoDefaultObject(language, "gmsMessage")) {
			language.addDefault("l.gmsMessage", "&2Gamemode switched to survival.");
		}
		if(!SetIfNoDefaultObject(language, "gmaMessage")) {
			language.addDefault("l.gmaMessage", "&2Gamemode switched to adventure.");
		}
		if(!SetIfNoDefaultObject(language, "gmspMessage")) {
			language.addDefault("l.gmspMessage", "&2Gamemode switched to spectator.");
		}
		if(!SetIfNoDefaultObject(language, "gmcMessageSender")) {
			language.addDefault("l.gmcMessageSender", "&2Gamemode switched to creative for %player%.");
		}
		if(!SetIfNoDefaultObject(language, "gmsMessageSender")) {
			language.addDefault("l.gmsMessageSender", "&2Gamemode switched to survival for %player%.");
		}
		if(!SetIfNoDefaultObject(language, "gmaMessageSender")) {
			language.addDefault("l.gmaMessageSender", "&2Gamemode switched to adventure for %player%.");
		}
		if(!SetIfNoDefaultObject(language, "gmspMessageSender")) {
			language.addDefault("l.gmspMessageSender", "&2Gamemode switched to spectator for %player%.");
		}
		if(!SetIfNoDefaultObject(language, "getPosMessage")) {
			language.addDefault("l.getPosMessage", "&6Position: &rX: %x% Y: %y% Z: %z%");
		}
		if(!SetIfNoDefaultObject(language, "godEnable")) {
			language.addDefault("l.godEnable", "&6God mode has been &cenabled.");
		}
		if(!SetIfNoDefaultObject(language, "godDisable")) {
			language.addDefault("l.godDisable", "&6God mode has been &cdisabled.");
		}
		if(!SetIfNoDefaultObject(language, "godEnableSender")) {
			language.addDefault("l.godEnableSender", "&6God mode has been &cenabled &6for &c%player%.");
		}
		if(!SetIfNoDefaultObject(language, "godDisableSender")) {
			language.addDefault("l.godDisableSender", "&6God mode has been &cdisabled &6for &c%player%.");
		}
		if(!SetIfNoDefaultObject(language, "hatMessage")) {
			language.addDefault("l.hatMessage", ":&6Enjoy your new hat!");
		}
		if(!SetIfNoDefaultObject(language, "healMessage")) {
			language.addDefault("l.healMessage", "&6You have been healed.");
		}
		if(!SetIfNoDefaultObject(language, "healMessageSender")) {
			language.addDefault("l.healMessageSender", "&6Player &c%player% &6has been healed");
		}
		if(!SetIfNoDefaultObject(language, "noHome")) {
			language.addDefault("l.noHome", "&cYou do not have a home!");
		}
		if(!SetIfNoDefaultObject(language, "homeNumber")) {
			language.addDefault("l.homeNumber", "&6Number of homes: ");
		}
		if(!SetIfNoDefaultObject(language, "homeNotFound")) {
			language.addDefault("l.homeNotFound", "&cHome %home% not found!");
		}
		if(!SetIfNoDefaultObject(language, "ignoreMessage")) {
			language.addDefault("l.ignoreMessage", "&6Player &c%player%&r &6has been ignored.");
		}
		if(!SetIfNoDefaultObject(language, "ignoreMessageNo")) {
			language.addDefault("l.ignoreMessageNo", "&6Player &c%player%&r &6is no long being ignored.");
		}
		if(!SetIfNoDefaultObject(language, "invSeeMessage")) {
			language.addDefault("l.invSeeMessage", "&6Player &c%player%&r's inventory has been opened.");
		}
		if(!SetIfNoDefaultObject(language, "invSeeOwn")) {
			language.addDefault("l.invSeeOwn", "&cCannot invsee your own inventory!");
		}
		if(!SetIfNoDefaultObject(language, "itemMessage")) {
			language.addDefault("l.itemMessage", "&6You have received &c%item%&6!");
		}
		if(!SetIfNoDefaultObject(language, "itemNotFound")) {
			language.addDefault("l.itemNotFound", "&cItem not found! (Use Bukkit Item ID's)");
		}
		if(!SetIfNoDefaultObject(language, "configReload")) {
			language.addDefault("l.configReload", "&6Configuration file reloaded.");
		}
		if(!SetIfNoDefaultObject(language, "cannotKick")) {
			language.addDefault("l.cannotKick", "&cThat player can't be kicked!");
		}
		if(!SetIfNoDefaultObject(language, "kickMessage")) {
			language.addDefault("l.kickMessage", "&cPlayer &c%player% &6has been kicked!");
		}
		if(!SetIfNoDefaultObject(language, "killMessage")) {
			language.addDefault("l.killMessage", "&6You have been killed.");
		}
		if(!SetIfNoDefaultObject(language, "killMessageSender")) {
			language.addDefault("l.killMessageSender", "&6Player &c%player% &r&6has been killed!");
		}
		if(!SetIfNoDefaultObject(language, "suicideMessage")) {
			language.addDefault("l.suicideMessage", "&6Player &c%player% &r&6has taken their own life.");
		}
		if(!SetIfNoDefaultObject(language, "noKits")) {
			language.addDefault("l.noKits", "&cNo kits yet!");
		}
		if(!SetIfNoDefaultObject(language, "kitNotFound")) {
			language.addDefault("l.kitNotFound", "&cThat kit was not found!");
		}
		if(!SetIfNoDefaultObject(language, "kitNoName")) {
			language.addDefault("l.kitNoName", "&cA kit cannot be created with that name!");
		}
		if(!SetIfNoDefaultObject(language, "kitMessage")) {
			language.addDefault("l.kitMessage", "&bYou have receieved kit &a%kit%&b!");
		}
		if(!SetIfNoDefaultObject(language, "kitCreated")) {
			language.addDefault("l.kitCreated", "&bKit &a%kit% &bhas been created!");
		}
		if(!SetIfNoDefaultObject(language, "kitDeleted")) {
			language.addDefault("l.kitDeleted", "&bThat kit was deleted!");
		}
		if(!SetIfNoDefaultObject(language, "kitFirstJoin")) {
			language.addDefault("l.kitFirstJoin", "&6Firstjoin for kit &c%kit% &6set to: &c%toggle%");
		}
		if(!SetIfNoDefaultObject(language, "listHeader")) {
			language.addDefault("l.listHeader", "&6There are &c%players% &6currently online:");
		}
		if(!SetIfNoDefaultObject(language, "listHidden")) {
			language.addDefault("l.listHidden", "&6And &c%hidden% &6hidden players online:");
		}
		if(!SetIfNoDefaultObject(language, "muted")) {
			language.addDefault("l.muted", "&6You are muted!");
		}
		if(!SetIfNoDefaultObject(language, "unmuted")) {
			language.addDefault("l.unmuted", "&6You have been unmuted!");
		}
		if(!SetIfNoDefaultObject(language, "mailNone")) {
			language.addDefault("l.mailNone", "&6You have no new mail.");
		}
		if(!SetIfNoDefaultObject(language, "mailFull")) {
			language.addDefault("l.mailFull", "&6You have &c%messages% &6new messages!");
		}
		if(!SetIfNoDefaultObject(language, "ignored")) {
			language.addDefault("l.ignored", "&c%player% &r&6player is currently ignoring you!");
		}
		if(!SetIfNoDefaultObject(language, "ssMail")) {
			language.addDefault("l.ssMail", "&2&lSocialSpy (Mail Sent): &r%mail%");
		}
		if(!SetIfNoDefaultObject(language, "ssMessage")) {
			language.addDefault("l.ssMessage", "&2&lSocialSpy (Private Message): &r%message%");
		}
		if(!SetIfNoDefaultObject(language, "mailSent")) {
			language.addDefault("l.mailSent", "&6Mail sent!");
		}
		if(!SetIfNoDefaultObject(language, "mailBox")) {
			language.addDefault("l.mailBox", "&6Your current mailbox:");
		}
		if(!SetIfNoDefaultObject(language, "mailAll")) {
			language.addDefault("l.mailAll", "&6Mail sent to all!");
		}
		if(!SetIfNoDefaultObject(language, "mailClear")) {
			language.addDefault("l.mailClear", "&6Your mailbox was cleared!");
		}
		if(!SetIfNoDefaultObject(language, "motdSet")) {
			language.addDefault("l.motdSet", "&2MOTD has been set to:");
		}
		if(!SetIfNoDefaultObject(language, "motdEnable")) {
			language.addDefault("l.motdEnable", "&6MOTD-enabled has been set to:");
		}
		if(!SetIfNoDefaultObject(language, "ignoreBypass")) {
			language.addDefault("l.ignoreBypass", "&c%player% has permission to bypass your ignoring them!");
		}
		if(!SetIfNoDefaultObject(language, "afkAnswer")) {
			language.addDefault("l.afkAnswer", "&5&o(This player is afk and might not answer!)");
		}
		if(!SetIfNoDefaultObject(language, "mutedSender")) {
			language.addDefault("l.mutedSender", "&6Player %player% muted.");
		}
		if(!SetIfNoDefaultObject(language, "unmutedSender")) {
			language.addDefault("l.unmutedSender", "&6Player %player% unmuted.");
		}
		if(!SetIfNoDefaultObject(language, "mutedSenderTimed")) {
			language.addDefault("l.mutedSenderTimed", "&6Player %player% muted for %mins% minutes.");
		}
		if(!SetIfNoDefaultObject(language, "cannotMute")) {
			language.addDefault("l.cannotMute", "&cCannot mute this player!");
		}
		if(!SetIfNoDefaultObject(language, "nickMessage")) {
			language.addDefault("l.nickMessage", "&6Your new nick has been set to:&r %nick%");
		}
		if(!SetIfNoDefaultObject(language, "nickMessageSender")) {
			language.addDefault("l.nickMessageSender", "&6Player %player%'s nick has been set.");
		}
		if(!SetIfNoDefaultObject(language, "nickReset")) {
			language.addDefault("l.nickReset", "&6Your nick has been reset!");
		}
		if(!SetIfNoDefaultObject(language, "payMessage")) {
			language.addDefault("l.payMessage", "&aSent %amount% to %player%");
		}
		if(!SetIfNoDefaultObject(language, "receivePay")) {
			language.addDefault("l.receivePay", "&aReceived %amount% from %player%");
		}
		if(!SetIfNoDefaultObject(language, "noMoney")) {
			language.addDefault("l.noMoney", "&cYou do not have enough money to do this!");
		}
		if(!SetIfNoDefaultObject(language, "ptimeDay")) {
			language.addDefault("l.ptimeDay", "&6Player time set to: &cday");
		}
		if(!SetIfNoDefaultObject(language, "ptimeNight")) {
			language.addDefault("l.ptimeNight", "&6Player time set to: &cnight");
		}
		if(!SetIfNoDefaultObject(language, "ptimeTicks")) {
			language.addDefault("l.ptimeTicks", "&6Player time set to: &c%ticks%");
		}
		if(!SetIfNoDefaultObject(language, "noMessages")) {
			language.addDefault("l.noMessages", "&6You have not messaged anyone recently!");
		}
		if(!SetIfNoDefaultObject(language, "realnameMessage")) {
			language.addDefault("l.realnameMessage", "&6Player &c%player%&r&6's realname is: &r%realname%");
		}
		if(!SetIfNoDefaultObject(language, "repairMessage")) {
			language.addDefault("l.repairMessage", "&6Your item has been repaired!");
		}
		if(!SetIfNoDefaultObject(language, "repairAll")) {
			language.addDefault("l.repairAll", "&6All your items have been repaired!");
		}
		if(!SetIfNoDefaultObject(language, "maxHomes")) {
			language.addDefault("l.maxHomes", "&cYou have set the max number of homes.");
		}
		if(!SetIfNoDefaultObject(language, "invalidHomeName")) {
			language.addDefault("l.invalidHomeName", "&cCannot create a home with that name!");
		}
		if(!SetIfNoDefaultObject(language, "setHomeMessage")) {
			language.addDefault("l.setHomeMessage", "&6Home set!");
		}
		if(!SetIfNoDefaultObject(language, "setSpawn")) {
			language.addDefault("l.setSpawn", "&6Spawn set!");
		}
		if(!SetIfNoDefaultObject(language, "setWarp")) {
			language.addDefault("l.setWarp", "&6Warp %warp% has been set!");
		}
		if(!SetIfNoDefaultObject(language, "invalidWarpName")) {
			language.addDefault("l.invalidWarpName", "&cInvalid warp name!");
		}
		if(!SetIfNoDefaultObject(language, "warpSet")) {
			language.addDefault("l.warpSet", "&cWarp already set!");
		}
		if(!SetIfNoDefaultObject(language, "worldSpawnSet")) {
			language.addDefault("l.worldSpawnSet", "&6World spawnpoint set!");
		}
		if(!SetIfNoDefaultObject(language, "ssEnable")) {
			language.addDefault("l.ssEnable", "&2SocialSpy enabled for: %player%");
		}
		if(!SetIfNoDefaultObject(language, "ssDisable")) {
			language.addDefault("l.ssDisable", "&2SocialSpy disabled for: %player%");
		}
		if(!SetIfNoDefaultObject(language, "noSpawn")) {
			language.addDefault("l.noSpawn", "&cNo spawn set!");
		}
		if(!SetIfNoDefaultObject(language, "spawnTeleporting")) {
			language.addDefault("l.spawnTeleporting", "&6Player teleporting to spawn...");
		}
		if(!SetIfNoDefaultObject(language, "sudoMessage")) {
			language.addDefault("l.sudoMessage", "&6Player &c%player% &6has been sudoed.");
		}
		if(!SetIfNoDefaultObject(language, "sudoExempt")) {
			language.addDefault("l.sudoExempt", "&cThis player is exempted from being sudoed!");
		}
		if(!SetIfNoDefaultObject(language, "tempbanTimes")) {
			language.addDefault("l.tempbanTimes", "&cInvalid time unit provided. Time units include:");
		}
		if(!SetIfNoDefaultObject(language, "timeDay")) {
			language.addDefault("l.timeDay", "&6Time set to: &cday");
		}
		if(!SetIfNoDefaultObject(language, "timeNight")) {
			language.addDefault("l.timeNight", "&6Time set to: &cnight");
		}
		if(!SetIfNoDefaultObject(language, "timeTicks")) {
			language.addDefault("l.timeTicks", "&6Time set to: &c%ticks%");
		}
		if(!SetIfNoDefaultObject(language, "tpOff")) {
			language.addDefault("l.tpOff", "&cPlayer has teleportation disabled!");
		}
		if(!SetIfNoDefaultObject(language, "playersTeleported")) {
			language.addDefault("l.playersTeleported", "&6Players teleported.");
		}
		if(!SetIfNoDefaultObject(language, "teleportingReceive")) {
			language.addDefault("l.teleportingReceive", "&6Player &c%player% &6is teleporting to you!");
		}
		if(!SetIfNoDefaultObject(language, "teleportingSent")) {
			language.addDefault("l.teleportingSent", "&6You are being teleported to: &c%player%");
		}
		if(!SetIfNoDefaultObject(language, "tpAccept")) {
			language.addDefault("l.tpAccept", "&6Teleport request accepted.");
		}
		if(!SetIfNoDefaultObject(language, "tpDeny")) {
			language.addDefault("l.tpDeny", "&6Teleport request denied.");
		}
		if(!SetIfNoDefaultObject(language, "requestExpired")) {
			language.addDefault("l.requestExpired", "&cTeleportation request expired.");
		}
		if(!SetIfNoDefaultObject(language, "requestSent")) {
			language.addDefault("l.requestSent", "&6Request sent.");
		}
		if(!SetIfNoDefaultObject(language, "noRequest")) {
			language.addDefault("l.noRequest", "&cYou do not have a pending request!");
		}
		if(!SetIfNoDefaultObject(language, "tptoggleOn")) {
			language.addDefault("l.tptoggleOn", "&6Teleportation toggled: &con");
		}
		if(!SetIfNoDefaultObject(language, "tptoggleOff")) {
			language.addDefault("l.tptoggleOff", "&6Teleportation toggled: &coff");
		}
		if(!SetIfNoDefaultObject(language, "notBanned")) {
			language.addDefault("l.notBanned", "&cThat player is not banned!");
		}
		if(!SetIfNoDefaultObject(language, "unbanMessage")) {
			language.addDefault("l.unbanMessage", "&6Player &c%player% &6has been unbanned.");
		}
		if(!SetIfNoDefaultObject(language, "vanishEnabled")) {
			language.addDefault("l.vanishEnabled", "&6Vanish mode set to: &cenabled");
		}
		if(!SetIfNoDefaultObject(language, "vanishDisabled")) {
			language.addDefault("l.vanishDisabled", "&6Vanish mode set to: &cdisabled");
		}
		if(!SetIfNoDefaultObject(language, "warpingPlayer")) {
			language.addDefault("l.warpingPlayer", "&6Teleporting player to warp: &c%warp%");
		}
		if(!SetIfNoDefaultObject(language, "noWarps")) {
			language.addDefault("l.noWarps", "&cNo warps found!");
		}
		if(!SetIfNoDefaultObject(language, "weatherClear")) {
			language.addDefault("l.weatherClear", "&cWeather set to: &cclear");
		}
		if(!SetIfNoDefaultObject(language, "weatherThunderstorm")) {
			language.addDefault("l.weatherThunderstorm", "&cWeather set to: &cthunderstorm");
		}
		if(!SetIfNoDefaultObject(language, "weatherRain")) {
			language.addDefault("l.weatherRain", "&cWeather set to: &crain");
		}
		if(!SetIfNoDefaultObject(language, "workbenchMessage")) {
			language.addDefault("l.workbenchMessage", "&6Workbench opened!");
		}
		if(!SetIfNoDefaultObject(language, "flySpeed")) {
			language.addDefault("l.flySpeed", "&6Flying speed for &c%player% &r&6set to &c%speed%");
		}
		if(!SetIfNoDefaultObject(language, "walkSpeed")) {
			language.addDefault("l.walkSpeed", "&6Walking speed for &c%player% &r&6set to &c%speed%");
		}
		if(!SetIfNoDefaultObject(language, "speedHigh")) {
			language.addDefault("l.speedHigh", "&cThat speed is too high!");
		}
		if(!SetIfNoDefaultObject(language, "kitDelaySet")) {
			language.addDefault("l.kitDelaySet", "&6Delay for kit &c%kit% &6set to &c%time% &6seconds.");
		}
		if(!SetIfNoDefaultObject(language, "moneyLost")) {
			language.addDefault("l.moneyLost", "&a%amount% has been taken from your balance.");
		}
		if(!SetIfNoDefaultObject(language, "moneyGain")) {
			language.addDefault("l.moneyGain", "&a%amount% has been added to your balance.");
		}
		if(!SetIfNoDefaultObject(language, "spawnSet")) {
			language.addDefault("l.spawnSet", "§6The spawnpoint has been set.");
		}
		if(!SetIfNoDefaultObject(language, "pluginOutdated")) {
			language.addDefault("l.pluginOutdated", "&6&l[JEssentials] &e&l-WARNING- &c&oYou are not running the latest version of the JEssentials plugin. Please check here for latest version;");
		}
		if(!SetIfNoDefaultObject(language, "onCooldown")) {
			language.addDefault("l.onCooldown", "&4Command &c%command% &4is on cooldown! &c%timeLeft% &4seconds before you can use it!");
		}
		if(!SetIfNoDefaultObject(language, "kitCooldown")) {
			language.addDefault("l.kitCooldown", "&4Kit &c%kit% &4is currently in cooldown! &c%timeLeft% &4seconds before you can use it!");
		}
		if(!SetIfNoDefaultObject(language, "invalidSignItem")) {
			language.addDefault("l.invalidSignItem", "&4Error occured while using sign: Invalid Item!");
		}
		if(!SetIfNoDefaultObject(language, "invalidSignPrice")) {
			language.addDefault("l.invalidSignPrice", "&4Error occured while using sign: Invalid Price! (Don't use a dollar sign!)");
		}
		if(!SetIfNoDefaultObject(language, "invalidSignAmount")) {
			language.addDefault("l.invalidSignAmount", "&4Error occured while using sign: Invalid Amount!");
		}
		if(!SetIfNoDefaultObject(language, "signSellInsufficient")) {
			language.addDefault("l.signSellInsufficient", "&cYou do not have enough of that item!");
		}
		if(!SetIfNoDefaultObject(language, "toppedUp")) {
			language.addDefault("l.toppedUp", "&aThe item you are holding has been topped up!");
		}
		if(!SetIfNoDefaultObject(language, "commandDeleted")) {
			language.addDefault("l.commandDeleted", "&cCommand &4%command% &chas been deleted.");
		}
		if(!SetIfNoDefaultObject(language, "commandNotFound")) {
			language.addDefault("l.commandNotFound", "&cCommand &4%command% &cwas not found. &r&f(Remember: Do not put the '/' in-front!)");
		}
		if(!SetIfNoDefaultObject(language, "noCommands")) {
			language.addDefault("l.noCommands", "&cNo custom commands!");
		}
		if(!SetIfNoDefaultObject(language, "kitGive")) {
			language.addDefault("l.kitGive", "&aKit &2%kit% &ahas been given to player &2%player%&a.");
		}
		
		try {
			language.options().copyDefaults();
			language.save(lang);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			language.options().copyDefaults(true);
			language.save(lang);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
