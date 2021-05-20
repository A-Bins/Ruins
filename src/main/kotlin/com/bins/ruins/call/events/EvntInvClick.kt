package com.bins.ruins.call.events

import com.bins.ruins.run.View
import com.bins.ruins.utilities.Util.bb
import com.comphenix.protocol.PacketType
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.server.ServerListPingEvent

@Suppress("DEPRECATION")
class EvntInvClick : Listener {

    @EventHandler
    fun event(e: InventoryClickEvent){
        if(e.slotType == InventoryType.SlotType.CRAFTING)
            e.isCancelled = true
        if(View.cancels.contains(e.view.title)) {
            if(e.currentItem?.type == Material.GRAY_STAINED_GLASS_PANE)
                e.isCancelled = true
        }
        if(View.views.contains(e.view.title)) {
            val p = e.whoClicked as Player
            val run = View(p)
            when(ChatColor.stripColor(e.currentItem?.itemMeta?.displayName)){
                "컨테이너" -> run.inContainer()
            }
            when(e.view.title){
                "컨테이너" -> {
                    if(e.currentItem?.type != Material.IRON_BARS)
                        return
                }
            }
            e.isCancelled = true
        }

    }
}