package com.bins.ruins.call.events

import com.bins.ruins.run.View
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EvtSwap : Listener{
    @EventHandler
    fun event(e: PlayerSwapHandItemsEvent){
        e.isCancelled = true
        View(e.player).inMenu()
    }
}