package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.structure.objects.utilities.MetaReceiver.toCrossBowMeta
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*


/*
val array: ArrayList<ItemStack> = arrayListOf()
for(i in 0..54){
    array.add(ItemStack(Material.STONE))
}
val d = Drawer(*array.toTypedArray(), unlockState = 1)
Strash(p.uniqueId, d)
*/

//val pm = ProtocolLibrary.getProtocolManager()
//val packet = pm.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
//packet.itemSlots.write(0, EnumWrappers.ItemSlot.MAINHAND)
//packet.itemModifier.write(
//0,
//ItemStack(Material.CROSSBOW).apply {
//    val meta = itemMeta.toCrossBowMeta()
//    meta.addChargedProjectile(ItemStack(Material.ARROW))
//    itemMeta = meta
//}
//)
//packet.integers.write(0, p.entityId)
//Ruins.instance.server.onlinePlayers.forEach {
//    pm.sendServerPacket(it, packet);
//}
@Suppress("DEPRECATION")
class test : CommandExecutor{
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
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            Ruins.instance.server.onlinePlayers.forEach {
                if(it.name == p.name) return@forEach
                ProtocolLibrary.getProtocolManager().sendServerPacket(it, entityEquipment(p.entityId ,EquipmentSlot.HAND, ItemStack(Material.CROSSBOW).apply {
                    val meta = itemMeta.toCrossBowMeta()
                    meta.addChargedProjectile(ItemStack(Material.ARROW))
                    itemMeta = meta
                }))
            }
        }
        return false
    }
}