package com.bins.ruins.call.events

import org.bukkit.event.EventHandler
import org.bukkit.event.server.ServerListPingEvent
@Suppress("DEPRECATION")
class EvntServerListPing {
    @EventHandler
    fun event(e: ServerListPingEvent){
        e.motd = ""
    }
}