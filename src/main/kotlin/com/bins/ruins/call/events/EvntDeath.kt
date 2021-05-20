package com.bins.ruins.call.events

import com.bins.ruins.Ruins
import com.bins.ruins.run.Vars
import com.bins.ruins.utilities.Util.bb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.metadata.FixedMetadataValue

class EvntDeath :Listener{
    @EventHandler
    fun event(e: PlayerDeathEvent){
    }
}