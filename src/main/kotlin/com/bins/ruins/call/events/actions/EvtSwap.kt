package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.classes.View
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EvtSwap : Listener{
    @EventHandler
    fun event(e: PlayerSwapHandItemsEvent){
        e.isCancelled = true
//        View(e.player).inMenu()
    }
}