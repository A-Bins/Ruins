package com.bins.ruins.call.events.actions.discord

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.structure.classes.Hideout
import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.persistence.PersistentDataType

@Suppress("DEPRECATION")
class EvtLogins: Listener {
    @DelicateCoroutinesApi
    private fun discord(p: Player, isJoin: Boolean) {

        val async = GlobalScope.async {
            CherryBlossom.minecrafts().forEach {
                Ruins.cherryBlossom.rest.channel.createMessage(it.id) {
                    embed {
                        color = Color(47, 49, 54)
                        author = EmbedBuilder.Author().apply {
                            icon = "https://crafatar.com/avatars/${p.uniqueId}?size=50"
                            name = "${p.name}님이 ${if(isJoin) "Ruins에 접속 하셨습니다" else "Ruins에서 퇴장 하셨습니다"}"
                        }
                    }
                }
            }
        }
    }
    @InternalCoroutinesApi
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
    private fun PlayerJoinEvent.check() {

        if(player.clientBrandName == null) {
            1L.rl { check() }
            return
        }
        Ruins.instance.server.operators.filter { it.isOnline }.forEach {
            it.player?.apply { sendMessage("§f${this@check.player.name}§7은 §8${this@check.player.clientBrandName} §7클라이언트를 쓴다구욘?") }
        }
    }
    private fun PlayerJoinEvent.auth(backCount: Int) {
        if (backCount == 30) player.kickPlayer("§c디스코드로 인증을 해주세요!")
        else if(!Auth.completers.containsValue(player.uniqueId)) {
            20L.rl {
                player.sendTitle("§c디스코드로 인증을 해주세요!", "", 5, 5, 5)
                auth(backCount+1)
            }
        }
    }
    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    @EventHandler
    fun event(e: PlayerJoinEvent) {
        e.auth(0)
        e.check()
        reload()
        discord(e.player, true)
        e.player.healthScale = 40.0
        e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
        Stash.default(e.player.uniqueId)
        Hideout.unlockList.forEach {
            if(!e.player.persistentDataContainer.has(it, PersistentDataType.STRING)) e.player.persistentDataContainer[it, PersistentDataType.STRING] = "false"
        }
        vars.totals.putIfAbsent(e.player.uniqueId, Total.create(0, 0))
    }
    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    @EventHandler
    fun event1(e: PlayerQuitEvent) {
        reload()
        discord(e.player, false)
        Hideout.arHash.remove(e.player.uniqueId)

    }
}