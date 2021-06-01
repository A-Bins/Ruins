package com.bins.ruins

import com.bins.ruins.call.commandTabs.LoreTab
import com.bins.ruins.call.commandTabs.NameTab
import com.bins.ruins.call.commands.Lore
import com.bins.ruins.call.commands.Name
import com.bins.ruins.call.commands.OpenStash
import com.bins.ruins.call.commands.test
import com.bins.ruins.call.events.actions.*
import com.bins.ruins.call.events.inventories.EvtInvClick
import com.bins.ruins.call.events.inventories.EvtInvClose
import com.bins.ruins.call.events.inventories.EvtInvOpen
import com.bins.ruins.call.events.others.EvtServerListPing
import com.bins.ruins.run.vars.container
import com.bins.ruins.run.vars.totals
import com.bins.ruins.run.vars.glowValue
import com.bins.ruins.run.vars.reload
import com.bins.ruins.run.View
import com.bins.ruins.run.vars.stashes
import com.bins.ruins.structure.enums.types.ReceiverType.*
import com.bins.ruins.utilities.ScoreBoards
import com.bins.ruins.utilities.Glows
import com.bins.ruins.utilities.Receiver.bb
import com.bins.ruins.utilities.Receiver.targetedItemEntity
import com.bins.ruins.utilities.Util.load
import com.bins.ruins.utilities.Util.save
import org.bukkit.ChatColor
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
        View.views.add("물품 보관함")
        dataFolder.mkdirs()

        load(this, reload, "reload", INT)
        load(this, container, "container", ITEMSTACK)
        load(this, totals, "Totals", TOTAL)
        load(this, stashes, "Stashes", STASH)

        reload.putIfAbsent("server", 1)
        reload["server"] = reload["server"]!!+1
        save(this, reload, "reload", INT)



        server.scheduler.runTask(this, Runnable {
            "${ChatColor.BOLD}두근 두근 리로드 횟수는! ${reload["server"]}".bb()
        })
        server.scheduler.runTaskTimerAsynchronously(this, Runnable {
            save(this, container, "container", ITEMSTACK)
            save(this, totals, "Totals", TOTAL)
            save(this, stashes, "Stashes", STASH)
        }, 5, 20*10)
        instance = this
        server.pluginManager.apply{
            registerEvents(EvtInvClick(), this@Ruins)
            registerEvents(EvtSwap(), this@Ruins)
            registerEvents(EvtNpcRightClick(), this@Ruins)
            registerEvents(EvtBlock(), this@Ruins)
            registerEvents(EvntStoneFile(), this@Ruins)
            registerEvents(EvtInvClose(), this@Ruins)
            registerEvents(EvtInteract(), this@Ruins)
            registerEvents(EvtInvOpen(), this@Ruins)
            registerEvents(EvtPickUp(), this@Ruins)
            registerEvents(EvtLogins(), this@Ruins)
            registerEvents(EvtServerListPing(), this@Ruins)
            registerEvents(EvtDeath(), this@Ruins)
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