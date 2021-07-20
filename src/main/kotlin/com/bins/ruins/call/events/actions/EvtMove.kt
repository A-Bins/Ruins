package com.bins.ruins.call.events.actions

import com.bins.ruins.cherryblossom.classes.Auth
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
@Suppress("DEPRECATION")
class EvtMove: Listener {
    @EventHandler
    fun event(e: PlayerMoveEvent){
        if(!Auth.completers.containsValue(e.player.uniqueId)) e.isCancelled = true
    }
}