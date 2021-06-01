package com.bins.ruins.utilities

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta
import org.bukkit.inventory.meta.*

class MetaReceiver {
    fun ItemMeta.toSkullMeta() = this as SkullMeta
    fun ItemMeta.toBookMeta() = this as BookMeta
    fun ItemMeta.toCompassMeta() = this as CrossbowMeta
    fun ItemMeta.toFireworkMeta() = this as FireworkMeta
    fun ItemMeta.toFireworkEffectMeta() = this as FireworkEffectMeta
    fun ItemMeta.toEnchantmentStorageMeta() = this as EnchantmentStorageMeta
    fun ItemMeta.toArmorStandMeta() = this as ArmorStandMeta
    fun ItemMeta.toBannerMeta() = this as BannerMeta
    fun ItemMeta.toBlockDataMeta() = this as BlockDataMeta
    fun ItemMeta.toBlockStateMeta() = this as BlockStateMeta
    fun ItemMeta.toKnowledgeBookMeta() = this as KnowledgeBookMeta
    fun ItemMeta.toLeatherArmorMeta() = this as LeatherArmorMeta
    fun ItemMeta.toSpawnEggMeta() = this as SpawnEggMeta
    fun ItemMeta.toPotionMeta() = this as PotionMeta
    fun ItemMeta.toSuspiciousStewMeta() = this as SuspiciousStewMeta
}