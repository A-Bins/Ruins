package com.bins.ruins.structure.interfaces

import org.bukkit.inventory.ItemStack

interface Printable {
    val failure: Double
    val signature: String
    val result: ItemStack
    val time: Int
    val kinds: String
    val print: ItemStack
    fun decoding()
}