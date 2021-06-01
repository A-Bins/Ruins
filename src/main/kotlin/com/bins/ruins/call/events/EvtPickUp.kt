package com.bins.ruins.call.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent

class EvtPickUp:Listener {
    @EventHandler
    fun event(e: PlayerAttemptPickupItemEvent){
        e.isCancelled = true
        e.flyAtPlayer = false
    }
}