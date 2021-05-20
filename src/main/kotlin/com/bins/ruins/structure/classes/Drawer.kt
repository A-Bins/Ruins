package com.bins.ruins.structure.classes

import org.bukkit.inventory.ItemStack
import java.util.ArrayList

class Drawer(vararg val items: ItemStack, val unlockState: Int) {
    init {
        if(items.size > unlockState){
            throw IllegalArgumentException("items's size are over unlockState's value $unlockState")
        }
    }
}