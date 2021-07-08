package com.bins.ruins.structure.classes

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

class Search(val loc: Location, val type: Material, vararg items: ItemStack) {
    val values = Triple(loc, type, items)
    init {
        components.add(this)
    }
    companion object {
        val components: ArrayList<Search> = arrayListOf()
        fun Block.get() = components.find { location == it.values.first && type == it.values.second }
    }
}