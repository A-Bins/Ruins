package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.structure.objects.utilities.MetaReceiver.toCrossBowMeta
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sun.audio.AudioPlayer.player
import java.util.*


/*
val array: ArrayList<ItemStack> = arrayListOf()
for(i in 0..54){
    array.add(ItemStack(Material.STONE))
}
val d = Drawer(*array.toTypedArray(), unlockState = 1)
Strash(p.uniqueId, d)
*/

@Suppress("DEPRECATION")
class test : CommandExecutor{
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){

            val pm = ProtocolLibrary.getProtocolManager()
            val packet = pm.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
            packet.itemSlots.write(0, EnumWrappers.ItemSlot.HEAD)
            packet.itemModifier.write(
                0,
                ItemStack(Material.CROSSBOW).apply {
                    val meta = itemMeta.toCrossBowMeta()
                    meta.addChargedProjectile(ItemStack(Material.ARROW))
                    itemMeta = meta
                }
            )
            packet.integers.write(0, p.entityId)
            Ruins.instance.server.onlinePlayers.forEach {
                pm.sendServerPacket(it, packet);
            }


        }
        return false
    }
}