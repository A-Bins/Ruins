package com.bins.ruins.call.events.inventories

import com.bins.ruins.structure.classes.Flea.Companion.flea
import com.bins.ruins.structure.classes.Flea.Companion.fleaCondition
import com.bins.ruins.structure.classes.Stash.Companion.inDrawers
import com.bins.ruins.structure.classes.Stash.Companion.stash
import com.bins.ruins.structure.classes.Stash.Companion.stashCondition
import com.bins.ruins.structure.classes.View
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryEvent

@Suppress("DEPRECATION")
object RuinsInventory {
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    private fun InventoryClickEvent.cancelCondition() = View.cancels.contains(view.title)
    private fun InventoryClickEvent.viewCondition() = View.views.contains(view.title)


    private fun InventoryClickEvent.cancel() {
        if(currentItem?.type == Material.GRAY_STAINED_GLASS_PANE)
            isCancelled = true

    }
    private fun InventoryClickEvent.view() {
        val p = whoClicked as Player
        val run = View(p)
        when{
            ChatColor.stripColor(currentItem?.itemMeta?.displayName) == "컨테이너" -> run.inContainer()
            view.title == "물품 보관함" -> {
                p.playSound(p.location, Sound.BLOCK_CHEST_OPEN, 1F, 1F)
                currentItem?.itemMeta?.displayName?.toInt()?.let { p.inDrawers(it) }
            }
        }
        isCancelled = true
    }


    private fun InventoryClickEvent.conditions() {
        if(cancelCondition()) cancel()
        if(viewCondition()) view()
        if(stashCondition()) stash()
        if(fleaCondition()) flea()
    }
    fun InventoryClickEvent.branch() {
        conditions()
    }
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

}