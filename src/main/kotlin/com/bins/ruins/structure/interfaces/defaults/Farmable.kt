package com.bins.ruins.structure.interfaces.defaults

import com.bins.ruins.structure.enums.types.ItemT
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Farmable {
    fun farming(p: Player, b: Block)
    val t: ItemT
    val label: String
    val itemStack: ItemStack
    val bundleAmount: Int
    val bundleItemStack: ItemStack
        get() {
            val item = itemStack.clone()
            item.amount = bundleAmount
            return item
        }
}