package com.bins.ruins.call.events.inventories

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
@Suppress("DEPRECATION")
class EvtInvOpen: Listener {
    private val info = ItemStack(Material.GRAY_STAINED_GLASS_PANE).apply {
        val meta = itemMeta
        meta.setDisplayName("§6재활용 기계 사용 방법")
        //&71. 분해 가능 물품을 넣는다" and "&72. 창을 닫는다" and "&73. 분해되는 걸 기다린다" and "&74. 끝! 가져가자"
        meta.lore = listOf(
            "",
            "§71. 분해 가능 물품을 넣는다",
            "§72. 창을 닫는다",
            "§73. 분해되는 걸 기다린다.",
            "§74. 끝! 가져가자"

        )
        itemMeta = meta
    }
    @EventHandler
    fun event(e: InventoryOpenEvent){
        val p = e.player

        when(e.view.title){
            "Barrel" ->{
                for(i in 0..9) {
                    e.inventory.setItem(i, info)
                }
                for(i in 17..26) {
                    e.inventory.setItem(i, info)
                }
            }
        }

    }
}