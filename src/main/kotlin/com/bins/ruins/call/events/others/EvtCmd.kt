package com.bins.ruins.call.events.others

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerCommandSendEvent

class EvtCmd: Listener {
    @EventHandler
    fun event(e: PlayerCommandPreprocessEvent) {
        if(!e.player.isOp) return

        if(e.message == "/rl confirm" || e.message == "/reload confirm") {
            e.isCancelled = true
            CoroutineScope(Dispatchers.Default).launch { CherryBlossom.cherryBlossomLogout() }
            5L.rl { Ruins.instance.server.dispatchCommand(Ruins.instance.server.consoleSender,"rl confirm") }
        }
    }
}