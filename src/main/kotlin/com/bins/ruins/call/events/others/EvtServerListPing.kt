package com.bins.ruins.call.events.others

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
@Suppress("DEPRECATION")
class EvtServerListPing: Listener{
    @EventHandler
    fun event(e: ServerListPingEvent){
        e.motd = "                       Ruins Season 2."
    }
}