package com.bins.ruins.call.events

import com.bins.ruins.run.vars
import com.bins.ruins.structure.classes.Drawer
import com.bins.ruins.structure.classes.Strash
import com.bins.ruins.structure.classes.Total
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EvtLogins: Listener {
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        Strash.default(e.player.uniqueId)
        vars.totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
    @EventHandler
    fun event1(e: PlayerQuitEvent) {
    }
}