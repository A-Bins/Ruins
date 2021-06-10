package com.bins.ruins.call.events.inventories

import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.classes.Stash.Companion.drawerSave
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

@Suppress("DEPRECATION")
class EvtInvClose: Listener {
    @EventHandler
    fun event(e: InventoryCloseEvent){
        val p = e.player as Player
        when{
            e.view.title == "Barrel" -> {
                val list : ArrayList<ItemStack> = ArrayList()
                for(i in 10..16){
                    list.add( if(e.inventory.getItem(i) == null) ItemStack(Material.AIR) else e.inventory.getItem(i)!!)
                }
                for(item in list){
                    item.bb()
                }
            }
            e.view.title == "컨테이너" -> {
                vars.container[p.uniqueId] = if(e.inventory.getItem(4) != null && e.inventory.getItem(4)?.type != Material.AIR) e.inventory.getItem(4)!! else ItemStack(Material.AIR)
            }
            e.view.title.contains("보관함") -> {
                p.drawerSave()
                p.playSound(p.location, Sound.BLOCK_CHEST_CLOSE, 1F, 1F)
            }
        }

    }
}