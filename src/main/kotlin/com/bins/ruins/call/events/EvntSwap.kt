package com.bins.ruins.call.events

import com.bins.ruins.run.view
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EvntSwap : Listener{
    @EventHandler
    fun Swap(e: PlayerSwapHandItemsEvent){
        e.isCancelled = true
        view(e.player).inMenu()
    }
}