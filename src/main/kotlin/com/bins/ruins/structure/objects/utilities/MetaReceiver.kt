package com.bins.ruins.structure.objects.utilities

import org.bukkit.inventory.meta.*

object MetaReceiver {
    fun ItemMeta.toSkullMeta() = this as SkullMeta
    fun ItemMeta.toBookMeta() = this as BookMeta
    fun ItemMeta.toCrossBowMeta() = this as CrossbowMeta
    fun ItemMeta.toCompassMeta() = this as CrossbowMeta
    fun ItemMeta.toFireworkMeta() = this as FireworkMeta
    fun ItemMeta.toFireworkEffectMeta() = this as FireworkEffectMeta
    fun ItemMeta.toEnchantmentStorageMeta() = this as EnchantmentStorageMeta
    fun ItemMeta.toBannerMeta() = this as BannerMeta
    fun ItemMeta.toBlockDataMeta() = this as BlockDataMeta
    fun ItemMeta.toBlockStateMeta() = this as BlockStateMeta
    fun ItemMeta.toKnowledgeBookMeta() = this as KnowledgeBookMeta
    fun ItemMeta.toLeatherArmorMeta() = this as LeatherArmorMeta
    fun ItemMeta.toSpawnEggMeta() = this as SpawnEggMeta
    fun ItemMeta.toPotionMeta() = this as PotionMeta
    fun ItemMeta.toSuspiciousStewMeta() = this as SuspiciousStewMeta
}