package com.bins.ruins.call.events.actions.discord

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.BroadcastMessageEvent
import org.bukkit.event.server.ServerLoadEvent

class EvtBroad: Listener {
    @EventHandler
    fun event(e: BroadcastMessageEvent) {
        if((e.message().children().firstOrNull() as? TextComponent)?.content()?.contains("Found update file for plugin") == true) { // Found update file for plugin
            CoroutineScope(Dispatchers.Default).launch {
                CherryBlossom.cherryBlossomLogout()
            }
        }
    }
}