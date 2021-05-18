package com.bins.ruins.structure.enums

import com.bins.ruins.structure.interfaces.Printable
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class BluePrint: Printable {
    TEST{

        override fun decoding() {

        }

        override val result: ItemStack
            get() = ItemStack(Material.AIR)
        override val time: Int
            get() = 60*60*1
        override val kinds: String
            get() = ""
        override val print: ItemStack
            get() = ItemStack(Material.AIR)
                override val failure: Double
            get() = 50.0
        override val signature: String
            get() = "테스트"

    },
    TES2T{
        override fun decoding() {

        }
        override val kinds: String
            get() = ""
        override val print: ItemStack
            get() = ItemStack(Material.AIR)
        override val result: ItemStack
            get() = ItemStack(Material.AIR)
        override val time: Int
            get() = 60*60*1
        override val failure: Double
            get() = 50.0
        override val signature: String
            get() = "테스트"
    }
}