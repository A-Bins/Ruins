package com.bins.ruins.call.events

import com.bins.ruins.run.Vars
import com.bins.ruins.structure.Total
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EvntJoin: Listener {
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        Vars.Totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
}