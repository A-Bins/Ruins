package com.bins.ruins.call.events.actions

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent

class EvtPickUp:Listener {
    @EventHandler
    fun event(e: EntityPickupItemEvent){
        e.isCancelled = true
    }
}