package com.bins.ruins.structure.objects

import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

class vars {
    companion object {
        val stashes: HashMap<UUID, Stash> = HashMap()
        val totals: HashMap<UUID, Total> = HashMap()
        val reload: HashMap<String, Int> = HashMap()
        val container: HashMap<UUID, ItemStack> = HashMap()
        val isClick: HashMap<UUID, Boolean> = HashMap()
        val glowValue: HashMap<UUID, Item?> = HashMap()
    }
}