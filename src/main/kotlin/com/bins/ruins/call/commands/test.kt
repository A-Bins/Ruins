package com.bins.ruins.call.commands

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLib
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import io.netty.util.internal.ThreadLocalRandom
import net.minecraft.server.v1_16_R3.Material.WOOL
import org.bukkit.Material
import org.bukkit.Tag.WOOL
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
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
//
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

            val packetContainer: PacketContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
            packetContainer.itemSlots.write(0, EnumWrappers.ItemSlot.HEAD)
            packetContainer.itemModifier.write(
                0,
                ItemStack(Material.WHITE_WOOL, 1, ThreadLocalRandom.current().nextInt(0, 10).toShort())
            )
            packetContainer.integers.write(0, p.entityId)

            ProtocolLibrary.getProtocolManager().sendServerPacket (p, packetContainer)

        }
        return false
    }
}