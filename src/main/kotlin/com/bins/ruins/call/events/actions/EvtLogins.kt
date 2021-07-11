package com.bins.ruins.call.events.actions

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.persistence.PersistentDataType

class EvtLogins: Listener {
    @DelicateCoroutinesApi
    fun reload() {
        1L.rl {
            val field = GlobalScope.async {
                Ruins.cherryBlossom.editPresence {
                    playing("${Bukkit.getOnlinePlayers().size}명이 Ruins를 플레이")
                }
            }
        }
    }
    var task = -1
    @DelicateCoroutinesApi
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        if(!Auth.requesters.containsValue(e.player.uniqueId)){
            task = (20L).rt {
                e.player.sendTitle("§c디스코드로 인증을 해주세요!", "", 20, 20, 20)
                if(Auth.requesters.containsValue(e.player.uniqueId)){
                    Ruins.scheduler.cancelTask(task)
                }
            }.taskId
            (20L*30).rl {
                if(!Auth.requesters.containsValue(e.player.uniqueId)) {
                    e.player.kickPlayer("§c디스코드로 인증을 해주세요!")
                    Ruins.scheduler.cancelTask(task)
                }
            }
        }
        reload()
        e.player.healthScale = 40.0
        e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
        Stash.default(e.player.uniqueId)
        Hideout.unlockList.forEach {
            if(!e.player.persistentDataContainer.has(it, PersistentDataType.STRING)) {
                e.player.persistentDataContainer[it, PersistentDataType.STRING] = "false"
            }
        }
        vars.totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
    @DelicateCoroutinesApi
    @EventHandler
    fun event1(e: PlayerQuitEvent) {
        reload()
        Hideout.arHash.remove(e.player.uniqueId)

    }
}