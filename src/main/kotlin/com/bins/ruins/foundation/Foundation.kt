package com.bins.ruins.foundation

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.foundation.structure.enums.Architecture
import com.bins.ruins.structure.classes.sessions.SessionMap.StreetOfAbin.second
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Foundation {
    companion object {
        val foundations: HashMap<UUID, Foundation> = HashMap()
        val Player.foundation: Foundation
            get() = foundations[uniqueId] ?: Foundation().also {
                foundations[uniqueId] = it
            }
    }
    var continued = true
    var currentArch: Architecture? = null
    fun build(arch: Architecture, player: Player, direction: String) {
        val loc = player.eyeLocation.toCenterLocation()
        repeat(3) {
            loc.add(player.eyeLocation.direction)
        }
        val follow = arch.follow("south", loc)
        loc.set(follow.x, follow.y, follow.z)
        arch.build(direction, loc)
        holoStop()
    }

    fun holo(
        arch: Architecture,
        player: Player,
        already: Boolean = false,
        fall: ArrayList<FallingBlock> = arrayListOf()
    ) {
        currentArch = arch
        if (!already) {
            continued = true
            val loc = player.eyeLocation.toCenterLocation()
            repeat(3) {
                loc.add(player.eyeLocation.direction)
            }
            val follow = arch.follow("south", loc)
            loc.set(follow.x, follow.y, follow.z)
            val falls = arrayListOf<FallingBlock>()
            val material: Material = if (arch.canBuild("south", loc))
                Material.LIGHT_BLUE_STAINED_GLASS else Material.RED_STAINED_GLASS

            arch.south().forEach {
                val locs = loc.clone().add(it.second).toCenterLocation().add(0.0, -0.5, 0.0)
                val f = player.world.spawnFallingBlock(locs, material.createBlockData())
                f.setGravity(false)
                falls.add(f)
            }
            1L.rl {
                holo(arch, player, true, falls)
            }
        } else {
            if (continued) {
                val archV: ArrayList<Pair<FallingBlock, Pair<Pair<Material, BlockData>, Location>>> = arrayListOf()
                arch.south().forEachIndexed { index, it ->
                    archV.add(fall[index] to it)
                }


                val loc = player.eyeLocation.toCenterLocation()
                repeat(3) {
                    loc.add(player.eyeLocation.direction)
                }
                val follow = arch.follow("south", loc)
                loc.set(follow.x, follow.y, follow.z)
                val material: Material = if (arch.canBuild("south", loc))
                    Material.LIGHT_BLUE_STAINED_GLASS else Material.RED_STAINED_GLASS

                archV.forEachIndexed { index, it ->
                    val locs = loc.clone().add(it.second.second).toCenterLocation().add(0.0, -0.5, 0.0)
                    it.first.remove()

                    fall[index] = player.world.spawnFallingBlock(locs, material.createBlockData()).apply {
                        setGravity(false)
                    }
                }
                1L.rl {
                    holo(arch, player, true, fall)
                }
            } else {
                fall.forEach {
                    it.remove()
                }
            }
        }
    }

    fun holoStop() {
        continued = false
        currentArch = null
    }
}