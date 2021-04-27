package com.bins.ruins.structure.interfaces

import com.bins.ruins.structure.ItemType
import org.bukkit.inventory.ItemStack

interface Farmable {
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