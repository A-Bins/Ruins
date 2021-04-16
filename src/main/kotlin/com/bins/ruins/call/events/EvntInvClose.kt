package com.bins.ruins.call.events

import com.bins.ruins.run.vars
import com.bins.ruins.utilities.Util.bb
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class EvntInvClose: Listener {
    @EventHandler
    fun event(e: InventoryCloseEvent){
        val p = e.player
        when(e.view.title){
            "컨테이너" -> {
                bb(e.view.title)
                vars.Container[p.uniqueId] = if(e.inventory.getItem(4) != null && e.inventory.getItem(4)?.type != Material.AIR) e.inventory.getItem(4)!! else ItemStack(Material.AIR)
                bb("${e.inventory.getItem(4)}")
            }
        }

    }
}