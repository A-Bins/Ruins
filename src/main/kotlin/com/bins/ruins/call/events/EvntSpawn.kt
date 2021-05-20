package com.bins.ruins.call.events

import com.bins.ruins.Ruins
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.metadata.FixedMetadataValue
import org.spigotmc.event.player.PlayerSpawnLocationEvent

class EvntSpawn: Listener {
    @EventHandler
    fun event(e: PlayerRespawnEvent){

        e.player.setMetadata("die", FixedMetadataValue(Ruins.instance, false))
    }
}