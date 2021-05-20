package com.bins.ruins.run

import com.bins.ruins.structure.classes.Strash
import com.bins.ruins.structure.classes.Total
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object Vars {
    val strashs: HashMap<UUID, Strash> = HashMap()
    val totals: HashMap<UUID, Total> = HashMap()
    val reload: HashMap<String, Int> = HashMap()
    val container: HashMap<UUID, ItemStack> = HashMap()
    val isClick: HashMap<UUID, Boolean> = HashMap()
    val glowValue: HashMap<UUID, Item?> = HashMap()
}