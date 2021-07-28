package com.bins.ruins.resistance.events

import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.Resistance.Companion.isGun
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.persistence.PersistentDataType

class EvtCheck:Listener {
    @EventHandler
    fun event(e: PlayerSwapHandItemsEvent) {
        e.isCancelled = true
        if(e.offHandItem?.isGun == true)  {
            val param = e.offHandItem!!.itemMeta!!.persistentDataContainer
            val show = when (param[Resistance.magazine, PersistentDataType.INTEGER]!!.toDouble() / param[Resistance.maxMagazine, PersistentDataType.INTEGER]!!.toDouble() * 100) {
                100.0 -> "                                         FULL ( 꽉참 )"
                in 75.0..99.9 -> "                                         Bigger than half ( 절반 보단 많음 )"
                in 50.0..74.9 -> "                                         About half ( 거의 절반임 )"
                in 25.0..49.9 -> "                                         Less than half ( 절반 보단 적음 )"
                in 0.1..24.9 -> "                                         Almost empty ( 거의 비었음 )"
                else -> "                                         EMPTY ( 비었음 )"
            }
            e.player.sendTitle(" ", show, 10, 40, 2)

            /*
            100 FULL
            99~75 BIGGER THAN HALF
            74~50 ABOUT HALF
            49~25 LESS THAN HALF
            24 1 ALMOST EMPTY
            0 EMPTY
            */
        }
    }
}