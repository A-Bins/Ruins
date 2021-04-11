package com.bins.ruins

import com.bins.ruins.call.events.EvntInvClick
import com.bins.ruins.call.events.EvntNpcRightClick
import com.bins.ruins.call.events.EvntSwap
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

    override fun onEnable() {
        server.pluginManager.also{
            it.registerEvents(EvntInvClick(), this)
            it.registerEvents(EvntSwap(), this)
            it.registerEvents(EvntNpcRightClick(), this)
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
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}