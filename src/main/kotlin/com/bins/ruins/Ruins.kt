package com.bins.ruins

import com.bins.ruins.call.commands.*
import com.bins.ruins.call.commands.tab.LoreTab
import com.bins.ruins.call.commands.tab.NameTab
import com.bins.ruins.call.events.actions.*
import com.bins.ruins.call.events.discord.EvtChat
import com.bins.ruins.call.events.discord.EvtLogins
import com.bins.ruins.call.events.farmings.EvtStoneFile
import com.bins.ruins.call.events.inventories.EvtInvClick
import com.bins.ruins.call.events.inventories.EvtInvClose
import com.bins.ruins.call.events.inventories.EvtInvOpen
import com.bins.ruins.call.events.others.EvtServerListPing
import com.bins.ruins.call.events.others.EvtTab
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.cherryblossom.classes.Auth.Companion.completers
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.structure.classes.Header
import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.classes.View
import com.bins.ruins.structure.enums.types.Receiver.*
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Glows
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.bins.ruins.structure.objects.utilities.Sidebars
import com.bins.ruins.structure.objects.utilities.Util.load
import com.bins.ruins.structure.objects.utilities.Util.save
import com.bins.ruins.structure.objects.vars.container
import com.bins.ruins.structure.objects.vars.glowValue
import com.bins.ruins.structure.objects.vars.reload
import com.bins.ruins.structure.objects.vars.stashes
import com.bins.ruins.structure.objects.vars.totals
import dev.kord.core.Kord
import kotlinx.coroutines.DelicateCoroutinesApi
import net.md_5.bungee.api.ChatColor
import org.bukkit.Location
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import java.io.File

//        val cherry = CherryBlossom.cherryBlossomInitializedAsync()

class Ruins : JavaPlugin(), CommandExecutor {
    @DelicateCoroutinesApi
    override fun onDisable() {
        val cherry = CherryBlossom.cherryBlossomLogoutAsync()
        hide.disable()
    }
    @DelicateCoroutinesApi
    override fun onEnable() {
        val cherry = CherryBlossom.cherryBlossomInitializedAsync()
        File(dataFolder.path+"/session").mkdirs()
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  */

        players = server.onlinePlayers
        instance = this
        scheduler = server.scheduler
        hide = Hideout()

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  */
        logger.warning(env.ENABLE_INFO.trimIndent())
        hide.enable()
        view()
        saveAndLoad()
        configCmd()
        configEvt()
//        moisture()
        targetGlow()
    }
    
    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile)
            f.createNewFile()
    }
    private fun seeToComponent(){

    }
    private fun saveAndLoad() {
        load(this, reload, "reload", INT)
        load(this, container, "container", ITEMSTACK)
        load(this, totals, "Totals", TOTAL)
        load(this, stashes, "Stashes", STASH)
        load(this, completers, "auths", AUTH)
        (20*10L).rtAsync(5) {
            save(this, container, "container", ITEMSTACK)
            save(this, totals, "Totals", TOTAL)
            save(this, stashes, "Stashes", STASH)
            save(this, completers, "auths", AUTH)
        }
        reload.putIfAbsent("server", 1)
        reload["server"] = reload["server"]!!+1
        save(this, reload, "reload", INT)
        20L.rl { "${ChatColor.BOLD}두근 두근 리로드 횟수는! ${reload["server"]}".bb() }
    }
    private fun configEvt() {
        server.pluginManager.apply{
            arrayOf(
                *Resistance.configs(), EvtMove(),
                EvtChat(), EvtInvClick(), EvtNpcRightClick(), EvtBlock(), EvtStoneFile(), EvtInvClose(), EvtInteract(),
                EvtInvOpen(), EvtPickUp(), EvtLogins(), EvtServerListPing(), EvtDeath(), EvtTab(), EvtDamage()
            ).forEach { registerEvents(it, this@Ruins) }
        }
    }
    private fun configCmd() {

        getCommand("인증")?.apply{
            setExecutor(AuthCommand())
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
        getCommand("spawn")?.apply{
            setExecutor(Spawn())
        }
    }
    private fun targetGlow() {
        1L.rt {
            server.onlinePlayers.forEach{ p ->

                p.playerListHeader = Header().header()
                Sidebars.showSidebar(p)
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
        }
    }
    private fun moisture() {
        (20*6L).rt {
            for(p in server.onlinePlayers){
                if(p.isOp)
                    return@rt
                if(Math.round((p.exp-0.01F)*100)/100.0 > 0.00)
                    p.exp -= 0.01F
                else {
                    if(p.exp != 0.0F)
                        p.exp = 0.0F
                    p.health -= 1
                }

            }
        }
    }
    private fun view() {
        View.cancels.add("Barrel")
        View.views.add("물품 보관함")
    }
    companion object{
        fun Long.rt(delay: Long = 1, run: Runnable) = scheduler.runTaskTimer(instance, run, delay, this)
        fun Long.rtAsync(delay: Long = 1, run: Runnable) = scheduler.runTaskTimerAsynchronously(instance, run, delay, this)
        fun Long.rl(run: Runnable) = scheduler.runTaskLater(instance, run, this)


        val Player.targetedItemEntity: Item?
            get() {
                (0..35).forEach { i ->
                    val loc : Location = eyeLocation.add(location.direction.multiply(i.toDouble()/10))
                    val list = loc.world!!.getNearbyEntities(loc, 0.15, 0.15, 0.15) { it is Item }

                    for (e in list) {
                        return e as Item
                    }
                }
                return null
            }
        lateinit var hide: Hideout
            private set
        lateinit var players: MutableCollection<out Player>
            private set
        lateinit var cherryBlossom: Kord
        lateinit var instance : Ruins
            private set
        lateinit var scheduler: BukkitScheduler
            private set
    }
}