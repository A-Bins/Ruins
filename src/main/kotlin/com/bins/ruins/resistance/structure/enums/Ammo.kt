package com.bins.ruins.resistance.structure.enums

import com.bins.ruins.resistance.Resistance
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


@Suppress("DEPRECATION")
enum class Ammo(val signature: String) {
    ABIN_5_56("ABIN 5.56mm") {
    },
    ACP_45(".45 ACP") {
    };
    fun ammo(): ItemStack = ItemStack(Material.WHEAT_SEEDS).apply {
        val meta = itemMeta
        meta!!.setDisplayName("§f$signature")
        meta.lore = arrayListOf("§7#무기 #탄약")
        meta.persistentDataContainer.set(
            Resistance.ammo,
            PersistentDataType.STRING,
            signature
        )
        itemMeta = meta
    }

}