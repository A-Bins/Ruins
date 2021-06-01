package com.bins.ruins.call.events.actions

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class EvtDeath :Listener{
    @EventHandler
    fun event(e: PlayerDeathEvent){
    }
}