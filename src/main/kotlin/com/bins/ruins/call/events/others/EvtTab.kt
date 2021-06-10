package com.bins.ruins.call.events.others

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandSendEvent

class EvtTab : Listener {
    @EventHandler
    fun event(e: PlayerCommandSendEvent){
        if(e.player.isOp) return
        e.commands.clear()
    }
}