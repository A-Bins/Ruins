package com.bins.ruins.run

import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object vars {
    val stashes: HashMap<UUID, Stash> = HashMap()
    val totals: HashMap<UUID, Total> = HashMap()
    val reload: HashMap<String, Int> = HashMap()
    val container: HashMap<UUID, ItemStack> = HashMap()
    val isClick: HashMap<UUID, Boolean> = HashMap()
    val glowValue: HashMap<UUID, Item?> = HashMap()
}