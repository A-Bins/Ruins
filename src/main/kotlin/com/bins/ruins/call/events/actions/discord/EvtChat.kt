package com.bins.ruins.call.events.actions.discord

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@Suppress("DEPRECATION")
class EvtChat: Listener {

    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    private fun discord(p: Player, msg: String) {
        CoroutineScope(Dispatchers.Default).launch {
            CherryBlossom.minecrafts().forEach {
                Ruins.cherryBlossom.rest.channel.createMessage(it.id) {
                    embed {
                        color = Color(47, 49, 54)
                        author = EmbedBuilder.Author().apply {
                            icon = "https://crafatar.com/avatars/${p.uniqueId}?size=50"
                            name = "${p.name}: $msg"
                        }
                    }
                }
            }
        }
    }
    @InternalCoroutinesApi
    @DelicateCoroutinesApi
    @EventHandler
    fun event(e: AsyncPlayerChatEvent) = discord(e.player, e.message)

}