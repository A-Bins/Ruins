package com.bins.ruins.structure.interfaces.items

import com.bins.ruins.structure.enums.types.SubTag
import com.bins.ruins.structure.enums.types.Tag
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface Item {
    val name: String
    val description: ArrayList<String>
    val item: ItemStack
    val tag: Tag
    val subTag: SubTag
}