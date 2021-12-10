package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.scavengers.Scavenger
import com.bins.ruins.scavengers.structure.enums.HearSound
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import net.citizensnpcs.nms.v1_17_R1.entity.EntityHumanNPC
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


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
//packet.everyone()
@Suppress("DEPRECATION")
class test : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return false }
        if(!p.isOp) return false
//            p.eyeLocation.neighbors(listOf( p.eyeLocation.add(p.eyeLocation.front()))).forEach {
//                it.block.type = Material.STONE
//            }
//            p.eyeLocation.add(p.eyeLocation.right()).block.type = Material.STONE
//            p.eyeLocation.add(p.eyeLocation.front()).block.type = Material.STONE
//            p.eyeLocation.add(p.eyeLocation.left()).block.type = Material.STONE
//            p.eyeLocation.add(p.eyeLocation.back()).block.type = Material.STONE


            if(args.isNotEmpty()){
                p.flySpeed = args[0].toFloat()
            }else{
                val scav = Scavenger(Location(p.world, -116.5, 20.0, -143.5))
//                val packet = PacketContainer(PacketType.Play.Server.CAMERA
            }
//                args[0].player?.apply {
//                    packet.integers.write(0, entityId)
//                }
//                p.packet(packet)
//            }
//            val scav = Scavenger(p.location)
//            (20*5L).rl {
//                scav.nearbyHear(HearSound.RUNNING, p.location)
//            }
//            val toast = Toast(NamespacedKey(Ruins.instance, "test"), "Bins", "DDang", "challenge", "apple")
//            toast.play(p)
//            SessionMap.StreetOfAbin.first.on(Map.STREET_OF_ABIN).also {
//                it.sessionKey.id.bb()
//            }
//            SessionMap.StreetOfAbin.first.off().also {
//                it.sessionKey.wasId.bb()
//            }









//            toast.chatComponents.write(0, WrappedChatComponent.fromText("real kk"))

//            pm.sendServerPacket(p, toast)

//            for(t in -100..100){
//                val l = p.eyeLocation.add(p.eyeLocation.direction.multiply(5)).apply {
//                    x += sin(0.05*t)
//                    y += sin(0.05*t)
//                }
//                l.world.spawnParticle(Particle.REDSTONE, l, 10, Particle.DustOptions(Color.AQUA, 1F))
//
//            }
//            if(args.isNotEmpty())
//                if(args[0] == "close") {
//                    val logout = CherryBlossom.cherryBlossomLogout()
//                    logout.key.bb()
//                }
//                else if(args[0] == "a") {
//                    val a: ArrayList<Int> = arrayListOf(1,5,3,2,76,10).also { it.sorted().bb() }
//                }
                Guns.WK416A5.give(p)
                p.inventory.addItem(Ammo.ABIN_5_56.ammo().apply {
                    amount = 64
                })
//            }
//            Guns.WK416A5.give(p)

        return false
    }
}