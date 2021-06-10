package com.bins.ruins.resistance

import com.bins.ruins.resistance.events.EvtSlotMove
import org.bukkit.event.Listener

class Resistance {
    companion object {
        fun configs(): Array<Listener> {
            return arrayOf(EvtSlotMove())
        }
    }
}