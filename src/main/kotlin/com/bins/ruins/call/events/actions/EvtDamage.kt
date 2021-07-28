package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class EvtDamage: Listener {
    @EventHandler
    fun event(e: EntityDamageEvent){
        if(e.cause == EntityDamageEvent.DamageCause.FALL) {
            if(e.entity is Player){
                val p = e.entity
                if(p.fallDistance >= 9) {
                    "허허 9칸 높이입니단".bb()

                }
                else if(p.fallDistance >= 6) {
                    "허허 6칸 높이입니단".bb()
                }
            }
        }
    }
}