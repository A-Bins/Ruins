package com.bins.ruins.structure.classes

import org.bukkit.inventory.ItemStack
import java.util.ArrayList

class Drawer(vararg val items: ItemStack, val unlockState: Int, val unlocked: Boolean) {
    init {
        if (unlockState < 1)
            throw IllegalArgumentException("unlockState need over 1, got $unlockState")
        if (unlockState > 13)
            throw IllegalArgumentException("unlockState don't over 13, got $unlockState")
        if(items.size > unlockState)
            throw IllegalArgumentException("items's size are over unlockState's value $unlockState")

    }
}