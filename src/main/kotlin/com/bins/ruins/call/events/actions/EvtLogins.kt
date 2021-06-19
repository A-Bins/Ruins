package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EvtLogins: Listener {
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        e.player.healthScale = 40.0
        e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
        Stash.default(e.player.uniqueId)
        vars.totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
    @EventHandler
    fun event1(e: PlayerQuitEvent) {
    }
}