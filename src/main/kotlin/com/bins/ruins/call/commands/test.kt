package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.classes.Toast
import com.bins.ruins.structure.classes.sessions.Session
import com.bins.ruins.structure.classes.sessions.SessionKey
import com.bins.ruins.structure.classes.sessions.SessionMap
import com.bins.ruins.structure.objects.utilities.Receiver.bb
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
import org.bukkit.entity.Player
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
//Ruins.instance.server.onlinePlayers.forEach {
//    pm.sendServerPacket(it, packet);
//}
@Suppress("DEPRECATION")
class test : CommandExecutor{
    @DelicateCoroutinesApi
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            val toast = Toast(NamespacedKey(Ruins.instance, "test"), "Bins", "DDang", "challenge", "apple")
            toast.play(p)
            SessionMap.StreetOfAbin.first.on()
            (SessionMap.StreetOfAbin.first.sessionKey as SessionKey.Exist).id.bb()









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