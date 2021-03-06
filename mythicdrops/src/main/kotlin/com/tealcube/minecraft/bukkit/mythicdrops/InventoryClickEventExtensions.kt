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
package com.tealcube.minecraft.bukkit.mythicdrops

import com.tealcube.minecraft.bukkit.mythicdrops.api.socketing.combiners.SocketGemCombinerGui
import io.pixeloutlaw.minecraft.spigot.hilt.getDisplayName
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.logging.Logger

fun InventoryClickEvent.getTargetItemAndCursorAndPlayer(logger: Logger): Triple<ItemStack, ItemStack, Player>? {
    val eventCurrentItem = currentItem
    val eventCursor = cursor
    if (eventCurrentItem == null || eventCursor == null) {
        logger.fine("eventCurrentItem == null || eventCursor == null")
        return null
    }
    val player = whoClicked as? Player ?: return null
    logger.fine("eventCurrentItem.type=${eventCurrentItem.type} eventCursor.type=${eventCursor.type} event.click=$click")
    if (eventCurrentItem.type == Material.AIR || eventCursor.type == Material.AIR ||
        click != ClickType.RIGHT
    ) {
        logger.fine("eventCurrentItem.type == Material.AIR || eventCursor.type == Material.AIR || event.click != ClickType.RIGHT")
        return null
    }

    val targetItem = eventCurrentItem.clone()
    val cursor = eventCursor.clone()
    val targetItemName = targetItem.getDisplayName()
    val cursorName = cursor.getDisplayName()

    if (targetItemName.isNullOrBlank() || cursorName.isNullOrBlank()) {
        logger.fine("targetItemName.isNullOrBlank() || cursorName.isNullOrBlank()")
        return null
    }

    return Triple(targetItem, cursor, player)
}

fun InventoryClickEvent.getTargetItemAndClickedInventoryAndPlayer(logger: Logger): Triple<ItemStack, Inventory, Player>? {
    val targetItem = currentItem
    if (targetItem == null || targetItem.type == Material.AIR) {
        logger.fine("currentItem == null || currentItem.type == Material.AIR")
        return null
    }
    val clickedInventory = clickedInventory
    if (clickedInventory == null || (clickedInventory.holder !is HumanEntity && clickedInventory.holder !is SocketGemCombinerGui)) {
        logger.fine("clickedInventory == null || (clickedInventory.holder !is HumanEntity && clickedInventory.holder !is SocketGemCombinerGui)")
        return null
    }

    val eventInventory = inventory
    val player = whoClicked as? Player ?: return null
    return Triple(targetItem, eventInventory, player)
}

fun InventoryClickEvent.updateCurrentItemAndSubtractFromCursor(currentItem: ItemStack) {
    // set the current item
    this.currentItem = currentItem

    // subtract one from the cursor, and if the amount drops below one, get rid of the item on the cursor
    cursor?.let {
        it.amount = it.amount - 1
        if (it.amount <= 0) {
            @Suppress("DEPRECATION")
            cursor = null
        }
    }

    isCancelled = true
    result = Event.Result.DENY

    @Suppress("DEPRECATION")
    (whoClicked as? Player)?.updateInventory()
}
