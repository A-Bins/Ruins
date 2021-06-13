package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.MetaReceiver.toCrossBowMeta
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan


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
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){

            for(i in 0..1000){
                val tan = p.eyeLocation.add(p.eyeLocation.direction.multiply(3))
                tan.x += cos(i.toDouble())
                tan.y += sin(i.toDouble())
                tan.world.spawnParticle(Particle.REDSTONE, tan, 1, Particle.DustOptions(Color.GREEN, 1F))
            }
//            Guns.HK416.give(p)
        }
        return false
    }
}