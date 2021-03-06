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
package com.tealcube.minecraft.bukkit.mythicdrops.utils

import com.tealcube.minecraft.bukkit.mythicdrops.MythicDropsPlugin
import com.tealcube.minecraft.bukkit.mythicdrops.api.tiers.Tier
import com.tealcube.minecraft.bukkit.mythicdrops.api.tiers.TierManager
import io.pixeloutlaw.minecraft.spigot.hilt.getDisplayName
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

object TierUtil {
    private val tierManager: TierManager
        get() = MythicDropsPlugin.getInstance().tierManager

    @JvmStatic
    fun getTier(name: String): Tier? = tierManager.getById(name) ?: tierManager.get().find {
        it.name.equals(
            name,
            ignoreCase = true
        ) || it.displayName.equals(name, ignoreCase = true)
    }

    @JvmStatic
    fun getTierFromItemStack(itemStack: ItemStack): Tier? = getTierFromItemStack(itemStack, tierManager.get())

    @JvmStatic
    fun getTierFromItemStack(itemStack: ItemStack, tiers: Collection<Tier>): Tier? {
        val displayName = itemStack.getDisplayName()
        if (displayName == null || displayName.isBlank()) {
            return null
        }
        val firstChatColor = ChatColorUtil.getFirstColor(displayName)
        val colors = ChatColor.getLastColors(displayName)
        val lastChatColor = if (colors.contains(ChatColor.COLOR_CHAR)) {
            ChatColor.getByChar(colors.substring(1, 2))
        } else {
            null
        }
        if (firstChatColor == null || lastChatColor == null || firstChatColor == lastChatColor) {
            return null
        }
        return tiers.find { it.displayColor == firstChatColor && it.identifierColor == lastChatColor }
    }
}
