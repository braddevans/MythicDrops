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

import org.bukkit.entity.Cow
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.Ghast
import org.bukkit.entity.MagmaCube
import org.bukkit.entity.Slime
import org.bukkit.entity.Wither
import org.bukkit.entity.Zombie
import org.bukkit.event.entity.CreatureSpawnEvent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CreatureSpawnEventUtilTest {
    val spawnReason = CreatureSpawnEvent.SpawnReason.DEFAULT
    @Mock
    lateinit var slime: Slime
    @Mock
    lateinit var magmaCube: MagmaCube
    @Mock
    lateinit var enderDragon: EnderDragon
    @Mock
    lateinit var zombie: Zombie
    @Mock
    lateinit var ghast: Ghast
    @Mock
    lateinit var cow: Cow
    @Mock
    lateinit var wither: Wither

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnTrueForCancelled() {
        val event = CreatureSpawnEvent(zombie, spawnReason)
        event.isCancelled = true

        Assert.assertTrue(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForSlime() {
        val event = CreatureSpawnEvent(slime, spawnReason)

        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForMagmaCube() {
        val event = CreatureSpawnEvent(magmaCube, spawnReason)

        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForEnderDragon() {
        val event = CreatureSpawnEvent(enderDragon, spawnReason)

        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForZombie() {
        val event = CreatureSpawnEvent(zombie, spawnReason)

        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForWither() {
        val event = CreatureSpawnEvent(wither, spawnReason)
        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnFalseForGhast() {
        val event = CreatureSpawnEvent(ghast, spawnReason)

        Assert.assertFalse(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }

    @Test
    fun doesShouldCancelDropsBasedOnCreatureSpawnEventReturnTrueForCow() {
        val event = CreatureSpawnEvent(cow, spawnReason)

        Assert.assertTrue(CreatureSpawnEventUtil.shouldCancelDropsBasedOnCreatureSpawnEvent(event))
    }
}
