package com.bins.ruins

import com.bins.ruins.call.commands.Lore
import com.bins.ruins.call.commands.Name
import com.bins.ruins.call.commands.OpenStash
import com.bins.ruins.call.commands.tab.LoreTab
import com.bins.ruins.call.commands.tab.NameTab
import com.bins.ruins.call.commands.test
import com.bins.ruins.call.events.actions.*
import com.bins.ruins.call.events.inventories.EvtInvClick
import com.bins.ruins.call.events.inventories.EvtInvClose
import com.bins.ruins.call.events.inventories.EvtInvOpen
import com.bins.ruins.call.events.others.EvtServerListPing
import com.bins.ruins.call.events.others.EvtTab
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.classes.View
import com.bins.ruins.structure.enums.types.ReceiverType.*
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Glows
import com.bins.ruins.structure.objects.utilities.MetaReceiver.toCrossBowMeta
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.bins.ruins.structure.objects.utilities.Receiver.targetedItemEntity
import com.bins.ruins.structure.objects.utilities.ScoreBoards
import com.bins.ruins.structure.objects.utilities.Util.load
import com.bins.ruins.structure.objects.utilities.Util.save
import com.bins.ruins.structure.objects.vars.container
import com.bins.ruins.structure.objects.vars.glowValue
import com.bins.ruins.structure.objects.vars.reload
import com.bins.ruins.structure.objects.vars.stashes
import com.bins.ruins.structure.objects.vars.totals
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.injector.netty.ProtocolInjector
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class Ruins : JavaPlugin(){
    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile)
            f.createNewFile()
    }
    companion object{
        lateinit var instance : Ruins
    }
    override fun onEnable() {
        instance = this
        server.scheduler.runTaskTimer(this, Runnable {
            for(p in server.onlinePlayers){
                if(p.isOp)
                    return@Runnable
                if(Math.round((p.exp-0.01F)*100)/100.0 > 0.00)
                    p.exp -= 0.01F
                else {
                    if(p.exp != 0.0F)
                        p.exp = 0.0F
                    if(p.level != 0)
                        p.level = 0
                    p.health -= 1
                }

            }
        }, 0, 20 * 6)
        View.cancels.add("Barrel")
        View.views.add("물품 보관함")
        dataFolder.mkdirs()
            //
        load(this, reload, "reload", INT)
        load(this, container, "container", ITEMSTACK)
        load(this, totals, "Totals", TOTAL)
        load(this, stashes, "Stashes", STASH)

        reload.putIfAbsent("server", 1)
        reload["server"] = reload["server"]!!+1
        save(this, reload, "reload", INT)
//
//
//        ProtocolLibrary.getProtocolManager().addPacketListener(
//            object : PacketAdapter(
//                this, ListenerPriority.NORMAL,
//                PacketType.Play.Server.SET_SLOT
//            ) {
//                override fun onPacketSending(event: PacketEvent) {
//                    if(Guns.values().toList().stream().anyMatch { event.packet.itemModifier.values[0].itemMeta?.displayName == it.displayName }) {
//                        event.packet.itemModifier.bb()
//                    }
//                }
//            })

        server.scheduler.runTask(this, Runnable {
            "${ChatColor.BOLD}두근 두근 리로드 횟수는! ${reload["server"]}".bb()
        })
        server.scheduler.runTaskTimerAsynchronously(this, Runnable {
            save(this, container, "container", ITEMSTACK)
            save(this, totals, "Totals", TOTAL)
            save(this, stashes, "Stashes", STASH)
        }, 5, 20*10)
        server.pluginManager.apply{
            arrayOf(
                *Resistance.configs(),
                EvtInvClick(), EvtSwap(), EvtNpcRightClick(), EvtBlock(), EvntStoneFile(), EvtInvClose(), EvtInteract(),
                EvtInvOpen(), EvtPickUp(), EvtLogins(), EvtServerListPing(), EvtDeath(), EvtTab(), EvtDamage()
            ).forEach { registerEvents(it, this@Ruins) }
        }
        getCommand("t")?.apply{
            setExecutor(test())
        }
        getCommand("stash")?.apply{
            setExecutor(OpenStash())
        }
        getCommand("name")?.apply{
            setExecutor(Name())
            tabCompleter = NameTab()
        }
        getCommand("lore")?.apply{
            setExecutor(Lore())
            tabCompleter = LoreTab()
        }
        logger.warning(env.ENABLE_INFO.trimIndent())
        server.scheduler.runTaskTimer(this, Runnable {
            for(p in server.onlinePlayers){
                ScoreBoards.showScoreboard(p)
                val e = p.targetedItemEntity
                if(e != null){
                    if(glowValue[p.uniqueId] != null){
                        Glows.setGlow(p, glowValue[p.uniqueId], false)
                        glowValue[p.uniqueId] = null
                    }
                    Glows.setGlow(p, e, true)
                    glowValue[p.uniqueId] = e
                }else if(glowValue[p.uniqueId] != null){
                    Glows.setGlow(p, glowValue[p.uniqueId], false)
                    glowValue[p.uniqueId] = null
                }
            }
        }, 1, 1)
    }
}