package com.bins.ruins.resistance.events

import com.bins.ruins.Ruins
import com.bins.ruins.resistance.structure.classes.Guns
import com.bins.ruins.structure.objects.utilities.MetaReceiver.toCrossBowMeta
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*

class EvtSlotMove: Listener {
    fun entityEquipment(entityId: Int, slot: EquipmentSlot, item: ItemStack): PacketContainer {
        fun EquipmentSlot.convertToItemSlot(): EnumWrappers.ItemSlot {
            return when (this) {
                EquipmentSlot.HAND -> EnumWrappers.ItemSlot.MAINHAND
                EquipmentSlot.OFF_HAND -> EnumWrappers.ItemSlot.OFFHAND
                EquipmentSlot.FEET -> EnumWrappers.ItemSlot.FEET
                EquipmentSlot.LEGS -> EnumWrappers.ItemSlot.LEGS
                EquipmentSlot.CHEST -> EnumWrappers.ItemSlot.CHEST
                EquipmentSlot.HEAD -> EnumWrappers.ItemSlot.HEAD
            }
        }
        return PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT).apply {
            integers.write(0, entityId)
            slotStackPairLists.write(0, Collections.singletonList(Pair(slot.convertToItemSlot(), item)))
        }
    }
    @EventHandler
    fun event(e: PlayerItemHeldEvent){
        val p = e.player
        if(Guns.values().toList().stream().anyMatch { p.inventory.getItem(e.newSlot)?.itemMeta?.displayName == it.displayName })
        Ruins.instance.server.onlinePlayers.forEach {
            if(it.name == p.name) return@forEach
            ProtocolLibrary.getProtocolManager().sendServerPacket(it, entityEquipment(p.entityId ,
                EquipmentSlot.HAND, ItemStack(Material.CROSSBOW).apply {
                val meta = itemMeta.toCrossBowMeta()
                meta.addChargedProjectile(ItemStack(Material.ARROW))
                itemMeta = meta
            }))
        }
    }
}