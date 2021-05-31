package com.bins.ruins

import com.bins.ruins.call.commandTabs.LoreTab
import com.bins.ruins.call.commandTabs.NameTab
import com.bins.ruins.call.commands.Lore
import com.bins.ruins.call.commands.Name
import com.bins.ruins.call.commands.test
import com.bins.ruins.call.events.*
import com.bins.ruins.run.Vars.container
import com.bins.ruins.run.Vars.totals
import com.bins.ruins.run.Vars.glowValue
import com.bins.ruins.run.Vars.reload
import com.bins.ruins.run.View
import com.bins.ruins.utilities.ScoreBoards
import com.bins.ruins.utilities.Glows
import com.bins.ruins.utilities.Util.bb
import com.bins.ruins.utilities.Util.load
import com.bins.ruins.utilities.Util.save
import com.bins.ruins.utilities.Util.targetedItemEntity
import com.bins.ruins.utilities.Util.tryCast
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
        dataFolder.mkdirs()

        load(this, reload, "reload")
        load(this, container, "container")
        load(this, totals, "Totals")

        reload.putIfAbsent("server", 1)
        reload["server"] = reload["server"]!!+1
        save(this, reload, "reload")



        server.scheduler.runTask(this, Runnable {
            bb("두근 두근 리로드 횟수는! ${reload["server"]}") })
        server.scheduler.runTaskTimerAsynchronously(this, Runnable {
            save(this, container, "container")
            save(this, totals, "Totals")
        }, 10*60, 5)
        instance = this
        server.pluginManager.apply{
            registerEvents(EvntInvClick(), this@Ruins)
            registerEvents(EvntSwap(), this@Ruins)
            registerEvents(EvntNpcRightClick(), this@Ruins)
            registerEvents(EvntBlock(), this@Ruins)
            registerEvents(EvntStoneFile(), this@Ruins)
            registerEvents(EvntInvClose(), this@Ruins)
            registerEvents(EvntInteract(), this@Ruins)
            registerEvents(EvntInvOpen(), this@Ruins)
            registerEvents(EvntPickUp(), this@Ruins)
            registerEvents(EvntJoin(), this@Ruins)
            registerEvents(EvntDeath(), this@Ruins)
        }
        getCommand("t")?.apply{
            setExecutor(test())
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