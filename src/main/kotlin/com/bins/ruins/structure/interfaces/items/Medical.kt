package com.bins.ruins.structure.interfaces.items

import com.bins.ruins.structure.enums.types.SubTag
import com.bins.ruins.structure.enums.types.Tag
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Medical: Item {
    override val description: ArrayList<String>
    override val Dname: String
    override val item: ItemStack
    override val subTag: SubTag
    override val tag: Tag
        get() = Tag.MEDICAL
    val time: Double
    fun effect(p: Player)
}