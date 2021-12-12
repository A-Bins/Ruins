package com.bins.ruins.foundation.events

import com.bins.ruins.foundation.Foundation.Companion.foundation
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EvtInteraction : Listener {
    @EventHandler
    fun event(e: PlayerSwapHandItemsEvent) {
        val p = e.player
        if(p.foundation.continued) {
            p.foundation.currentArch ?: return
            if(p.isSneaking) {
                p.foundation.build(p.foundation.currentArch!!, p, "south")
            }
            else {
                //방향 전환
            }
        }
    }
}