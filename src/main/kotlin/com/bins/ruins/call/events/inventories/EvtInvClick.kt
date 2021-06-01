package com.bins.ruins.call.events.inventories

import com.bins.ruins.run.View
import com.bins.ruins.structure.classes.Stash.Companion.inDrawers
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

@Suppress("DEPRECATION")
class EvtInvClick : Listener {

    @EventHandler
    fun event(e: InventoryClickEvent){
        if(View.cancels.contains(e.view.title)) {
            if(e.currentItem?.type == Material.GRAY_STAINED_GLASS_PANE)
                e.isCancelled = true
        }
        if(View.views.contains(e.view.title)) {
            val p = e.whoClicked as Player
            val run = View(p)
            when{
                ChatColor.stripColor(e.currentItem?.itemMeta?.displayName) == "컨테이너" -> run.inContainer()
                e.view.title == "물품 보관함" -> {
                    p.playSound(p.location, Sound.BLOCK_CHEST_OPEN, 1F, 1F)
                    e.currentItem?.itemMeta?.displayName?.toInt()?.let { p.inDrawers(it) }
                }
            }
            e.isCancelled = true
        }else if(e.view.title.contains("보관함")){
            val p = e.whoClicked as Player
            if(e.currentItem?.type == Material.ANVIL || e.currentItem?.type == Material.IRON_BARS)
                e.isCancelled = true
        }

    }
}