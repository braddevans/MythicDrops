/*
 * This file is part of MythicDrops, licensed under the MIT License.
 *
 * Copyright (C) 2019 Richard Harrah
 *
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tealcube.minecraft.bukkit.mythicdrops.anvil;

import com.tealcube.minecraft.bukkit.mythicdrops.api.settings.SettingsManager;
import com.tealcube.minecraft.bukkit.mythicdrops.api.socketing.SocketGem;
import com.tealcube.minecraft.bukkit.mythicdrops.api.tiers.Tier;
import com.tealcube.minecraft.bukkit.mythicdrops.utils.SocketGemUtil;
import com.tealcube.minecraft.bukkit.mythicdrops.utils.TierUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class AnvilListener implements Listener {

  private final SettingsManager settingsManager;

  public AnvilListener(SettingsManager settingsManager) {
    this.settingsManager = settingsManager;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onItemRename(InventoryClickEvent e) {
    if (e.isCancelled()) {
      return;
    }
    if (!settingsManager.getConfigSettings().getComponents().isRepairingEnabled()) {
      return;
    }
    HumanEntity ent = e.getWhoClicked();
    if (!(ent instanceof Player)) {
      return;
    }
    Inventory inv = e.getInventory();
    if (!(inv instanceof AnvilInventory)) {
      return;
    }
    ItemStack fis = inv.getItem(0);
    ItemStack sis = inv.getItem(1);

    if (settingsManager.getConfigSettings().getOptions().isAllowItemsToBeRepairedByAnvil()) {
      preventGems(fis, sis, e);
    } else {
      preventTiersAndGems(fis, sis, e);
    }
  }

  private void preventTiersAndGems(ItemStack fis, ItemStack sis, InventoryClickEvent e) {
    Tier ft = fis != null ? TierUtil.INSTANCE.getTierFromItemStack(fis) : null;
    Tier st = sis != null ? TierUtil.INSTANCE.getTierFromItemStack(sis) : null;
    SocketGem fsg = fis != null ? SocketGemUtil.getSocketGemFromItemStack(fis) : null;
    SocketGem stg = sis != null ? SocketGemUtil.getSocketGemFromItemStack(sis) : null;
    if ((ft != null || st != null || fsg != null || stg != null) && e.getSlot() == 2) {
      e.setCancelled(true);
      e.setResult(Event.Result.DENY);
    }
  }

  private void preventGems(ItemStack fis, ItemStack sis, InventoryClickEvent e) {
    SocketGem fsg = fis != null ? SocketGemUtil.getSocketGemFromItemStack(fis) : null;
    SocketGem stg = sis != null ? SocketGemUtil.getSocketGemFromItemStack(sis) : null;
    if ((fsg != null || stg != null) && e.getSlot() == 2) {
      e.setCancelled(true);
      e.setResult(Event.Result.DENY);
    }
  }
}
