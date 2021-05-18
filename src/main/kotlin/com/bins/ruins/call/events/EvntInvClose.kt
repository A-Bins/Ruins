package com.bins.ruins.call.events

import com.bins.ruins.run.Vars
import com.bins.ruins.utilities.Util.bb
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

@Suppress("DEPRECATION")
class EvntInvClose: Listener {
    @EventHandler
    fun event(e: InventoryCloseEvent){
        e.getPlayer()
        val p = e.player
        when(e.view.title){
            "Barrel" -> {
                val list : ArrayList<ItemStack> = ArrayList()
                for(i in 10..16){
                    list.add( if(e.inventory.getItem(i) == null) ItemStack(Material.AIR) else e.inventory.getItem(i)!!)
                }
                for(item in list){
                    bb(item)
                }
            }
            "컨테이너" -> {
                Vars.Container[p.uniqueId] = if(e.inventory.getItem(4) != null && e.inventory.getItem(4)?.type != Material.AIR) e.inventory.getItem(4)!! else ItemStack(Material.AIR)
            }
        }

    }
}