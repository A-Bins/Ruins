package com.bins.ruins.structure.interfaces

import com.bins.ruins.structure.enums.types.ItemType
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Farmable {
    fun farming(p: Player, b: Block)
    val signature: String
    val type: ItemType
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