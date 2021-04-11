package com.bins.ruins.call.events

import com.bins.ruins.run.View
import com.bins.ruins.utilities.Util.bb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class EvntInvClick : Listener {
    @EventHandler
    fun event(e: InventoryClickEvent){

        if(View.views.contains(e.view.title)) {
            bb(View.views)
            e.isCancelled = true
        }
    }
}