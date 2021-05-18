package com.bins.ruins.run

import com.bins.ruins.structure.classes.Total
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object Vars {
    val Totals: HashMap<UUID, Total> = HashMap()
    val reload: HashMap<String, Int> = HashMap()
    val Container: HashMap<UUID, ItemStack> = HashMap()
    val isClick: HashMap<UUID, Boolean> = HashMap()
    val glowValue: HashMap<UUID, Item?> = HashMap()
}