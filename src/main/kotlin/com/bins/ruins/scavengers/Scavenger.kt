package com.bins.ruins.scavengers

import com.bins.ruins.scavengers.structure.enums.HearSound
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import net.minecraft.world.entity.EntityInsentient
import net.minecraft.world.entity.EntityLiving
import net.minecraft.world.entity.ai.goal.PathfinderGoal
import net.minecraft.world.level.pathfinder.Pathfinder
import net.minecraft.world.level.pathfinder.PathfinderAbstract
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.*
import org.bukkit.entity.EntityType

class Scavenger(val spawn: Location) {
    val scav = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "스캐브")
    init {
        scav.spawn(spawn)
    }
    private val entity = scav.entity

    fun nearbyHear(sound: HearSound, soundLoc: Location) {
        if(sound.distance <= entity.location.distance(soundLoc)) return
        move(soundLoc)
    }
    private fun move(loc: Location) = scav.navigator.setTarget(loc)


}