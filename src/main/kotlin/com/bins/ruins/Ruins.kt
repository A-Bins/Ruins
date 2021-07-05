package com.bins.ruins

import com.bins.ruins.call.commands.*
import com.bins.ruins.call.commands.tab.LoreTab
import com.bins.ruins.call.commands.tab.NameTab
import com.bins.ruins.call.events.actions.*
import com.bins.ruins.call.events.inventories.EvtInvClick
import com.bins.ruins.call.events.inventories.EvtInvClose
import com.bins.ruins.call.events.inventories.EvtInvOpen
import com.bins.ruins.call.events.others.EvtServerListPing
import com.bins.ruins.call.events.others.EvtTab
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.structure.classes.Header
import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.classes.View
import com.bins.ruins.structure.enums.types.Receiver.*
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.env.BOT_TOKEN
import com.bins.ruins.structure.objects.utilities.Glows
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.bins.ruins.structure.objects.utilities.ScoreBoards
import com.bins.ruins.structure.objects.utilities.Util.load
import com.bins.ruins.structure.objects.utilities.Util.save
import com.bins.ruins.structure.objects.vars.container
import com.bins.ruins.structure.objects.vars.glowValue
import com.bins.ruins.structure.objects.vars.reload
import com.bins.ruins.structure.objects.vars.stashes
import com.bins.ruins.structure.objects.vars.totals
import dev.kord.core.Kord
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import java.io.File

//        val cherry = CherryBlossom.cherryBlossomInitializedAsync()

class Ruins : JavaPlugin() {
    override fun onDisable() {
        hide.disable()
    }
    @DelicateCoroutinesApi
    override fun onEnable() {
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  */

        players = server.onlinePlayers
        instance = this
        scheduler = server.scheduler
        hide = Hideout()

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  */
        logger.warning(env.ENABLE_INFO.trimIndent())
        dataFolder.mkdirs()
        hide.enable()
        view()
        saveAndLoad()
        count()
        configCmd()
        configEvt()
//        moisture()
        targetGlow()
    }
    
    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile)
            f.createNewFile()
    }
    private fun saveAndLoad() {
        load(this, reload, "reload", INT)
        load(this, container, "container", ITEMSTACK)
        load(this, totals, "Totals", TOTAL)
        load(this, stashes, "Stashes", STASH)
        server.scheduler.runTaskTimerAsynchronously(this, Runnable {
            save(this, container, "container", ITEMSTACK)
            save(this, totals, "Totals", TOTAL)
            save(this, stashes, "Stashes", STASH)
        }, 5, 20*10)
        reload.putIfAbsent("server", 1)
        reload["server"] = reload["server"]!!+1
        save(this, reload, "reload", INT)
    }
    private fun configEvt() {
        server.pluginManager.apply{
            arrayOf(
                *Resistance.configs(),
                EvtInvClick(), EvtNpcRightClick(), EvtBlock(), EvtStoneFile(), EvtInvClose(), EvtInteract(),
                EvtInvOpen(), EvtPickUp(), EvtLogins(), EvtServerListPing(), EvtDeath(), EvtTab(), EvtDamage()
            ).forEach { registerEvents(it, this@Ruins) }
        }
    }
    private fun configCmd() {
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
        getCommand("spawn")?.apply{
            setExecutor(Spawn())
        }
    }
    private fun targetGlow() {
        server.scheduler.runTaskTimer(this, Runnable {
            server.onlinePlayers.forEach{ p ->

                p.playerListHeader = Header().header()
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
    private fun moisture() {
        server.scheduler.runTaskTimer(this, Runnable {
            for(p in server.onlinePlayers){
                if(p.isOp)
                    return@Runnable
                if(Math.round((p.exp-0.01F)*100)/100.0 > 0.00)
                    p.exp -= 0.01F
                else {
                    if(p.exp != 0.0F)
                        p.exp = 0.0F
                    p.health -= 1
                }

            }
        }, 0, 20 * 6)
    }
    private fun view() {
        View.cancels.add("Barrel")
        View.views.add("물품 보관함")
    }
    private fun count() {
        server.scheduler.runTask(this, Runnable {
            "${ChatColor.BOLD}두근 두근 리로드 횟수는! ${reload["server"]}".bb()
        })
    }
    companion object{
        fun Long.rt(delay: Long, run: () -> Any): BukkitTask {
            return scheduler.runTaskTimer(instance, Runnable {
                run()
            }, delay, this)
        }
        fun Long.rl(run: () -> Any): BukkitTask {
            return scheduler.runTaskLater(instance, Runnable {
                run()
            }, this)
        }

        val Player.targetedItemEntity: Item?
            get() {
                (0..35).forEach { i ->
                    val loc : Location = eyeLocation.add(location.direction.multiply(i.toDouble()/10))
                    val list = loc.world.getNearbyEntitiesByType(Item::class.java, loc, 0.15, 0.15, 0.15)

                    for (e in list) {
                        return e
                    }
                }
                return null
            }
        lateinit var hide: Hideout
            private set
        lateinit var players: Collection<Player>
            private set
        lateinit var cherryBlossom: Kord
        lateinit var scheduler: BukkitScheduler
            private set
        lateinit var instance : Ruins
            private set
    }
}