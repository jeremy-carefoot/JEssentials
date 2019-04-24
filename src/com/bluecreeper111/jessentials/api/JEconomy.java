package com.bluecreeper111.jessentials.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class JEconomy implements Economy {
	
	public static File econ = new File("plugins//JEssentials//economy.yml");
	public static YamlConfiguration economy = YamlConfiguration.loadConfiguration(econ);

	@Override
	public EconomyResponse bankBalance(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPlayerAccount(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String currencyNamePlural() {
		return "\\$";
	}

	@Override
	public String currencyNameSingular() {
		return "\\$";
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot add zero dollars!");
		}
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		economy.set("players." + playerName + ".balance", balance + amount);
		try {
			economy.save(econ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EconomyResponse(amount, balance + amount, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		String playerName = player.getName();
		if (amount < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot add zero dollars!");
		}
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		economy.set("players." + playerName + ".balance", balance + amount);
		try {
			economy.save(econ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EconomyResponse(amount, balance + amount, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBalance(String playerName) {
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		return balance;
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		String playerName = player.getName();
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		return balance;
	}

	@Override
	public double getBalance(String playerName, String world) {
		return 0;
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean has(String playerName, double amount) {
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		if (balance < amount) {
			return false;
		}
		return true;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		String playerName = player.getName();
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		if (balance < amount) {
			return false;
		}
		return true;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
		}
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		if ((balance - amount) < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Insufficient funds!");
		}
		economy.set("players." + playerName + ".balance", balance - amount);
		try {
			economy.save(econ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EconomyResponse(amount, balance - amount, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		String playerName = player.getName();
		if (amount < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
		}
		double balance = 0;
		if (economy.isSet("players." + playerName + ".balance")) {
			balance = economy.getDouble("players." + playerName + ".balance");
		}
		if ((balance - amount) < 0) {
			return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Insufficient funds!");
		}
		economy.set("players." + playerName + ".balance", balance - amount);
		try {
			economy.save(econ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EconomyResponse(amount, balance - amount, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
