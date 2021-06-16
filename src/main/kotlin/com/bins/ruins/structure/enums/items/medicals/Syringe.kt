package com.bins.ruins.structure.enums.items.medicals

import com.bins.ruins.structure.enums.types.SubTag
import com.bins.ruins.structure.enums.types.Tag
import com.bins.ruins.structure.interfaces.items.Medical
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Syringe: Medical {
    TEST{
        override val description: ArrayList<String> = arrayListOf()
        override val item = ItemStack(Material.IRON_HORSE_ARMOR).apply {

        }
        override val subTag = SubTag.SYRINGE
        override val time = 5
        override fun effect() {

        }

    }
}