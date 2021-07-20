package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.scavengers.Scavenger
import com.bins.ruins.scavengers.structure.enums.HearSound
import com.bins.ruins.structure.classes.Toast
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.classes.sessions.Session
import com.bins.ruins.structure.classes.sessions.SessionKey
import com.bins.ruins.structure.classes.sessions.SessionMap
import com.bins.ruins.structure.enums.defaults.Map
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.bins.ruins.structure.objects.utilities.Receiver.packet
import com.bins.ruins.structure.objects.utilities.Receiver.player
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.DelicateCoroutinesApi
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.json.simple.JSONObject
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
//packet.everyone()
@Suppress("DEPRECATION")
class test : CommandExecutor{
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            label.bb()
            if(args.isNotEmpty()){
                val packet = PacketContainer(PacketType.Play.Server.CAMERA)
                args[0].player?.apply {
                    packet.integers.write(0, entityId)
                }
                p.packet(packet)
            }
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
//                    val logout = CherryBlossom.cherryBlossomLogoutAsync()
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
        }
        return false
    }
}