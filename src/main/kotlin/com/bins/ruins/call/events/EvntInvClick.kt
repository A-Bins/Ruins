package com.bins.ruins.call.events

import com.bins.ruins.run.view
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EvntInvClick : Listener {
    @EventHandler
    fun Click(e: InventoryClickEvent){

        if(e.view.title == "메뉴") {
            e.isCancelled = true
        }
    }
}