package com.bins.ruins.run

import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import java.util.*

object Vars {
    val Container: HashMap<UUID, ItemStack> = HashMap()
    val isClick: HashMap<UUID, Boolean> = HashMap()
    val glowValue: HashMap<UUID, Item?> = HashMap()
}