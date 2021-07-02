package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.persistence.PersistentDataType

class EvtLogins: Listener {
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        e.player.healthScale = 40.0
        e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
        Stash.default(e.player.uniqueId)
        Hideout.unlockList.forEach {
            if(!e.player.persistentDataContainer.has(it, PersistentDataType.STRING)) {
                e.player.persistentDataContainer[it, PersistentDataType.STRING] = "false"
            }
        }
        vars.totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
    @EventHandler
    fun event1(e: PlayerQuitEvent) {
        (Hideout.arHash[e.player.uniqueId] != null).bb()

        Hideout.arHash.remove(e.player.uniqueId)
        (Hideout.arHash[e.player.uniqueId] != null).bb()

    }
}