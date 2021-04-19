package com.bins.ruins

import com.bins.ruins.call.commands.test
import com.bins.ruins.call.events.*
import com.bins.ruins.run.View
import com.bins.ruins.run.vars.Container
import com.bins.ruins.run.vars.glowValue
import com.bins.ruins.utilities.Glows
import com.bins.ruins.utilities.Util.getTargetedItemEntity
import com.bins.ruins.utilities.Util.loadItemStack
import com.bins.ruins.utilities.Util.saveItemStack
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Ruins : JavaPlugin(), CommandExecutor {
    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile) {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    companion object{
        lateinit var instance : Ruins

    }

    override fun onEnable() {
        View.cancels.add("Barrel")
        dataFolder.mkdirs()
        loadItemStack(this, Container, "Container")


        Bukkit.getScheduler().runTaskTimer(this, Runnable {

            saveItemStack(this, Container, "Container")
        }, 10, 5)
        instance = this
        server.pluginManager.also{
            it.registerEvents(EvntInvClick(), this)
            it.registerEvents(EvntSwap(), this)
            it.registerEvents(EvntNpcRightClick(), this)
            it.registerEvents(EvntBlockBreak(), this)
            it.registerEvents(EvntStoneFile(), this)
            it.registerEvents(EvntInvClose(), this)
            it.registerEvents(EvntInteract(), this)
            it.registerEvents(EvntInvOpen(), this)
            it.registerEvents(EvntPickUp(), this)
        }
        getCommand("t")?.apply{
            setExecutor(test())
        }
        logger.warning("""
            루인스 플러그인 활성화!
            
            
                       * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                       *                                                     *
                       *               Ruins 플러그인 지침                   *
                       *                                                     *
                       *   이 플러그인은 A_bins(Bins#1004)가 만들었으며      *
                       *  Ruins 서버의 주 코어부분의 플러그인임을 알립니다.  *
                       *                                                     *
                       *          기획 : DDang_    제작 : A_bins             *
                       *                                                     *
                       *                                                     *
                       *   < Copyright 2021. Ruins. All rights reserved. >   *
                       *                                                     *
                       * * * * * * * * * * * * * * * * * * * * * * * * * * * *
      
      
      
        """.trimIndent())
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            for(p in server.onlinePlayers){
                val e = getTargetedItemEntity(p)
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

    override fun onDisable() {
        // Plugin shutdown logic
    }
}