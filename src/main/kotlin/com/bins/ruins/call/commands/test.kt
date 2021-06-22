package com.bins.ruins.call.commands

import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.enums.items.medicals.Syringe
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.math.cos
import kotlin.math.sin


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
//            for(i in 0..20){
//                val tan = p.eyeLocation.add(p.eyeLocation.direction.multiply(5))
//                tan.x += sin(7 *(i.toDouble() / 20))
//                tan.y += cos(7 *(i.toDouble() / 20))
//                tan.world.spawnParticle(Particle.REDSTONE, tan, 10, Particle.DustOptions(Color.GREEN, 1F))
//
//            }
            if(args.isNotEmpty()){
                if(args[0] == "LEMON") {
                    Syringe.catch("§f레몬 주사기").effect(p)
                }
                Guns.WK416A5.give(p)
                p.inventory.addItem(Ammo.ABIN_5_56.ammo().apply {
                    amount = 64
                })
            }
//            Guns.WK416A5.give(p)
        }
        return false
    }
}