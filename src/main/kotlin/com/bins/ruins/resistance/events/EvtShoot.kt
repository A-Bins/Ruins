package com.bins.ruins.resistance.events

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.Resistance.Companion.isGun
import com.bins.ruins.resistance.Resistance.Companion.resistance
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.resistance.structure.enums.Mode
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.stream.Collectors

@Suppress("DEPRECATION")
class EvtShoot : Listener {
    @EventHandler
    fun event(e: PlayerInteractEvent){
        if(e.action != Action.RIGHT_CLICK_BLOCK && e.action != Action.RIGHT_CLICK_AIR) return
        if(e.item?.isGun == true) {
            val gun = Guns.values().toList().stream().filter { e.item?.itemMeta?.displayName == it.displayName }.collect(Collectors.toList())[0]
            gun.shoot(e.player, e.item!!)
            if(e.player.resistance.isAuto() && (gun.mode == Mode.SEMI_AUTO || gun.mode == Mode.AUTO)){
                val i = (20 /(gun.rpm / 60)).toLong().rt {
                    gun.shoot(e.player, e.item!!)
                }.taskId
                4L.rl {
                    Ruins.scheduler.cancelTask(i)
                }
            }
        }
    }
}