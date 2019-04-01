package com.bluecreeper111.jessentials.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class cooldownManager {

	private final Map<Player, Map<String, Long>> cooldown = new HashMap<>();

	public void setCooldown(Player p, Long time, String command) {
		if (time < 1) {
			if (cooldown.get(p) == null) {
				cooldown.put(p, new HashMap<String, Long>());
			}
			Map<String, Long> map = cooldown.get(p);
			map.remove(command);
			cooldown.put(p, map);
		} else {
			if (cooldown.get(p) == null) {
				cooldown.put(p, new HashMap<String, Long>());
			}
			Map<String, Long> map = cooldown.get(p);
			map.put(command, time);
			cooldown.put(p, map);
		}

	}

	public Long getCooldown(Player p, String command) {
		if (cooldown.get(p) == null) {
			cooldown.put(p, new HashMap<String, Long>());
		}
		Map<String, Long> map = cooldown.get(p);
		return map.getOrDefault(command, 0L);
	}

}
