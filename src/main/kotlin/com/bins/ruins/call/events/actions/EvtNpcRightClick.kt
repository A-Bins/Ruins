package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.classes.View
import net.citizensnpcs.api.event.NPCRightClickEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EvtNpcRightClick : Listener {
    @EventHandler
    fun event(e: NPCRightClickEvent){}
}