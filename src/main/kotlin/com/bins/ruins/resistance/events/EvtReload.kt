package com.bins.ruins.resistance.events

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.Resistance.Companion.isGun
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import java.util.*

class EvtReload: Listener {
    @EventHandler
    fun event2(e: InventoryClickEvent) {
        if (e.slot == -999) {
            e.whoClicked.setMetadata("drop", FixedMetadataValue(Ruins.instance,true))
            1L.rl {
                e.whoClicked.setMetadata("drop", FixedMetadataValue(Ruins.instance,false))
            }


        }
    }
    @EventHandler
    fun event(e: PlayerDropItemEvent) {
        if(e.itemDrop.itemStack.isGun) {
            if(e.player.hasMetadata("drop"))
                if(e.player.getMetadata("drop")[0].asBoolean()) return
            val meta = e.itemDrop.itemStack.itemMeta
            lateinit var item: ItemStack
            val max = meta!!.persistentDataContainer[Resistance.maxMagazine, PersistentDataType.INTEGER]!!
            val current = meta.persistentDataContainer[Resistance.magazine, PersistentDataType.INTEGER]!!
            if(e.player.inventory.contents.toList().stream().anyMatch {
                    if(it == null) return@anyMatch false
                    if(it.itemMeta!!.persistentDataContainer.has(Resistance.ammo, PersistentDataType.STRING)) {
                        val nbt = meta.persistentDataContainer[Resistance.ammo, PersistentDataType.STRING] == it.itemMeta!!.persistentDataContainer[Resistance.ammo, PersistentDataType.STRING]
                        if(nbt && it.type == Material.WHEAT_SEEDS) {
                            item = it
                            return@anyMatch true
                        }else false
                    }else false
                }) {
                val has = (max - current) <= item.amount
                if(has) {
                    meta.persistentDataContainer[Resistance.magazine, PersistentDataType.INTEGER] = max
                    item.amount -= max - current

                }else {
                    meta.persistentDataContainer[Resistance.magazine, PersistentDataType.INTEGER] = item.amount + current
                    item.amount = 0
                }
                e.itemDrop.itemStack.itemMeta = meta
            }else "장전 실패".bb()
            e.isCancelled = true
        }
    }
}