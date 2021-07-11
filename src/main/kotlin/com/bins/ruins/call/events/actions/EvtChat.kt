package com.bins.ruins.call.events.actions

import com.bins.ruins.Ruins
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import dev.kord.core.entity.Guild
import dev.kord.core.entity.channel.GuildChannel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.server.BroadcastMessageEvent

class EvtChat: Listener {

    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    private fun discordToMc(target: String) {

        val async = GlobalScope.async {
            flow {

                Ruins.cherryBlossom.guilds.collect { emit(it) }
            }.collect {
                flow {
                    it.channels.collect {
                        emit(it)
                    }
                }.collect {
                    if(it.asChannel().data.topic.value?.contains("#Minecraft") == true) {
                        Ruins.cherryBlossom.rest.channel.createMessage(it.id) {
                            this.content = target
                        }
                    }
                }
            }
        }
    }
    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    @EventHandler
    fun event2(e: BroadcastMessageEvent) = discordToMc(e.message)
    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    @EventHandler
    fun event(e: AsyncPlayerChatEvent) = discordToMc("<${e.player.name}> ${e.message}")

}