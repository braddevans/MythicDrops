package net.nunnerycode.bukkit.mythicdrops.utils;

import net.nunnerycode.bukkit.mythicdrops.api.tiers.Tier;
import net.nunnerycode.bukkit.mythicdrops.tiers.TierMap;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class TierUtil {

	private TierUtil() {
		// do nothing
	}

	public static Tier randomTier() {
		return TierMap.getInstance().getRandom();
	}

	public static Tier randomTierWithChance() {
		return randomTierWithChance("default");
	}

	public static Tier randomTierWithChance(String worldName) {
		Validate.notNull(worldName, "String cannot be null");
		return TierMap.getInstance().getRandomWithChance(worldName);
	}

	public static Tier randomTier(Collection<Tier> collection) {
		Validate.notNull(collection, "Collection<Tier> cannot be null");
		Tier[] array = collection.toArray(new Tier[collection.size()]);
		return array[RandomUtils.nextInt(array.length)];
	}

	public static Tier randomTierWithChance(Collection<Tier> values) {
		Validate.notNull(values, "Collection<Tier> cannot be null");
		return randomTierWithChance(values, "default");
	}

	public static Tier randomTierWithChance(Collection<Tier> values, String worldName) {
		Validate.notNull(values, "Collection<Tier> cannot be null");
		Validate.notNull(worldName, "String cannot be null");
		Tier randomTier = null;
		Set<Tier> zeroSize = new HashSet<Tier>();
		while (randomTier == null && zeroSize.size() < values.size()) {
			Tier t = randomTier(values);
			if (zeroSize.contains(t)) {
				continue;
			}
			if (!worldName.equalsIgnoreCase("default") && !t.getWorldSpawnChanceMap().containsKey(worldName)) {
				zeroSize.add(t);
				continue;
			}
			if (RandomUtils.nextDouble() < t.getWorldSpawnChanceMap().get(worldName)) {
				randomTier = t;
			}
		}
		return randomTier;
	}

	public static Collection<Tier> getTiersFromStrings(Collection<String> strings) {
		Validate.notNull(strings, "Collection<String> cannot be null");
		Set<Tier> tiers = new LinkedHashSet<>();
		for (String s : strings) {
			Tier t = getTier(s);
			if (t != null) {
				tiers.add(t);
			}
		}
		return tiers;
	}

	public static Tier getTier(String name) {
		Validate.notNull(name, "String cannot be null");
		for (Tier t : TierMap.getInstance().values()) {
			if (t.getName().equals(name)) {
				return t;
			}
			if (t.getDisplayName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	public static List<String> getStringsFromTiers(Collection<Tier> collection) {
		Validate.notNull(collection, "Collection<Tier> cannot be null");
		List<String> col = new ArrayList<>();
		for (Tier t : collection) {
			col.add(t.getName());
		}
		return col;
	}

	private static ChatColor findColor(final String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == (char) 167 && (i + 1) < c.length) {
				return ChatColor.getByChar(c[i + 1]);
			}
		}
		return null;
	}

	public static Tier getTierFromItemStack(ItemStack itemStack) {
		Validate.notNull(itemStack);
		if (!itemStack.hasItemMeta()) {
			return null;
		}
		if (!itemStack.getItemMeta().hasDisplayName()) {
			return null;
		}
		String displayName = itemStack.getItemMeta().getDisplayName();
		ChatColor initColor = findColor(displayName);
		String colors = ChatColor.getLastColors(displayName);
		ChatColor endColor = ChatColor.getLastColors(displayName).contains(String.valueOf(ChatColor.COLOR_CHAR)) ?
				ChatColor.getByChar(colors.substring(1, 2)) : null;
		if (initColor == null || endColor == null || initColor == endColor) {
			return null;
		}
		for (Tier t : TierMap.getInstance().values()) {
			if (t.getDisplayColor() == initColor && t.getIdentificationColor() == endColor) {
				return t;
			}
		}
		return null;
	}

}