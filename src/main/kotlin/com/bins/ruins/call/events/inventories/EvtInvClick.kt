package com.bins.ruins.call.events.inventories

import com.bins.ruins.call.events.inventories.RuinsInventory.branch
import com.bins.ruins.structure.classes.Flea
import com.bins.ruins.structure.classes.View
import com.bins.ruins.structure.classes.Stash.Companion.inDrawers
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@Suppress("DEPRECATION")
class EvtInvClick : Listener {

    @EventHandler
    fun event(e: InventoryClickEvent) = e.branch()

}