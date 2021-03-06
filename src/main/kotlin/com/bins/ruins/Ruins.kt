package com.bins.ruins

import com.bins.ruins.call.commands.*
import com.bins.ruins.call.commands.tab.LoreTab
import com.bins.ruins.call.commands.tab.NameTab
import com.bins.ruins.call.events.actions.*
import com.bins.ruins.call.events.actions.discord.EvtBroad
import com.bins.ruins.call.events.actions.discord.EvtChat
import com.bins.ruins.call.events.actions.discord.EvtLogins
import com.bins.ruins.call.events.farmings.EvtStoneFile
import com.bins.ruins.call.events.inventories.EvtInvClick
import com.bins.ruins.call.events.inventories.EvtInvClose
import com.bins.ruins.call.events.inventories.EvtInvOpen
import com.bins.ruins.call.events.others.EvtCmd
import com.bins.ruins.call.events.others.EvtServerListPing
import com.bins.ruins.call.events.others.EvtTab
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.cherryblossom.classes.Auth.Companion.completers
import com.bins.ruins.foundation.events.EvtInteraction
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.structure.classes.Header
import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.classes.View
import com.bins.ruins.structure.enums.types.Receiver.*
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Glows
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import com.bins.ruins.structure.objects.utilities.Sidebars
import com.bins.ruins.structure.objects.utilities.Util.Companion.load
import com.bins.ruins.structure.objects.utilities.Util.Companion.save
import com.bins.ruins.structure.objects.vars.Companion.container
import com.bins.ruins.structure.objects.vars.Companion.glowValue
import com.bins.ruins.structure.objects.vars.Companion.reload
import com.bins.ruins.structure.objects.vars.Companion.stashes
import com.bins.ruins.structure.objects.vars.Companion.totals
import dev.kord.core.Kord
import kotlinx.coroutines.*
import org.bukkit.FluidCollisionMode
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import java.io.File
import java.lang.Runnable
import java.security.Security
import kotlin.math.round

@Suppress("DEPRECATION")
 class Ruins : JavaPlugin(), CommandExecutor {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDisable() {
        hide.disable()
//        CoroutineScope(Dispatchers.Default).launch {
//            CherryBlossom.cherryBlossomLogout()
//        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onEnable() {

        System.setProperty("io.ktor.random.secure.random.provider", "DRBG")
        Security.setProperty("securerandom.drbg.config", "HMAC_DRBG,SHA-512,256,pr_and_reseed")
        File(dataFolder.path+"/session").mkdirs()
/* ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????  */
        players = server.onlinePlayers
        instance = this
        scheduler = server.scheduler
        hide = Hideout()
/* ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????  */
        logger.warning(env.ENABLE_INFO.trimIndent())
//        hide.enable()
        view()
        saveAndLoad()
        configCmd()
        configEvt()
//        moisture()
        targetGlow()
        CoroutineScope(Dispatchers.Default).launch {
            CherryBlossom.cherryBlossomInit()
        }
    }





    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile)
            f.createNewFile()
    }
    private fun seeToComponent() {

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
        1L.rl {
            "????????? ??????.. ${reload["server"]}??? ??? ????????????".bb()
            println("????????? (*??????*) ??????????????? ${reload["server"]!! * 5}?????? ???????????????!")
                    println("??? ???????????????????????? ${reload["server"]!! * 4}?????? ???????????????!")
            val total = (reload["server"]!! * 5) + (reload["server"]!! * 4)

            println("????????? ??? ${total.toDouble()} ???, ${
                round((total / 60.0) * 100) / 100.0
            } ???, ${
                round((total / 60.0 / 60.0) * 100) / 100.0
            } ????????? ????????????!!")
        }
    }
    private fun configEvt() {
        server.pluginManager.apply{
            arrayOf(
                *Resistance.configs(), EvtMove(),
                EvtChat(), EvtInvClick(), EvtNpcRightClick(), EvtBlock(), EvtStoneFile(), EvtInvClose(), EvtInteract(),
                EvtInvOpen(), EvtPickUp(), EvtLogins(), EvtServerListPing(), EvtDeath(), EvtTab(), EvtDamage(), EvtBroad(), EvtCmd(), EvtInteraction()
            ).forEach { registerEvents(it, this@Ruins) }
        }
    }
    private fun configCmd() {

        getCommand("??????")?.apply{
            setExecutor(AuthCommand())
        }
        getCommand("t")?.apply{
            setExecutor(test())
        }
        getCommand("gun")?.apply{
            setExecutor(GiveGun())
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
        View.views.add("?????? ?????????")
    }








    companion object {
        fun Long.rt(delay: Long = 1, run: Runnable) = scheduler.runTaskTimer(instance, run, delay, this)
        fun Long.rtAsync(delay: Long = 1, run: Runnable) = scheduler.runTaskTimerAsynchronously(instance, run, delay, this)
        fun rAsync(run: Runnable) = scheduler.runTaskAsynchronously(instance, run)
        fun r(run: Runnable) = scheduler.runTask(instance, run)
        fun Long.rl(run: Runnable) = scheduler.runTaskLater(instance, run, this)
        fun Long.rlAsync(run: Runnable) = scheduler.runTaskLaterAsynchronously(instance, run, this)


        val Player.targetedItemEntity: Item?
            get() = world.rayTrace(eyeLocation, eyeLocation.direction, 3.0, FluidCollisionMode.NEVER, false, 0.25) { it is Item }?.hitEntity as? Item

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