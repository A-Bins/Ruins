package com.bins.ruins.call.events

import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent

class EvntPickUp:Listener {
    fun event(e: PlayerAttemptPickupItemEvent){
        e.isCancelled = true
    }
}