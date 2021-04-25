package com.bins.ruins.call.events

import com.bins.ruins.Ruins
import com.bins.ruins.run.Vars
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class EvntInteract : Listener{
    @EventHandler
    fun event(e: PlayerInteractEvent){
        val b = e.clickedBlock
        val p = e.player
        if(Vars.isClick[p.uniqueId] == true)
            return
        Vars.isClick[p.uniqueId] = true
        Bukkit.getScheduler().runTaskLater(Ruins.instance, Runnable{
            Vars.isClick[p.uniqueId] = false
        }, 2)

        if((e.action == Action.RIGHT_CLICK_AIR) or (e.action == Action.RIGHT_CLICK_BLOCK)) {
            if (Vars.glowValue[p.uniqueId] != null) {

                p.inventory.addItem(Vars.glowValue[p.uniqueId]!!.itemStack)
                Vars.glowValue[p.uniqueId]!!.remove()
                Vars.glowValue[p.uniqueId] = null
                p.playSound(p.location, Sound.ENTITY_ITEM_PICKUP, 1F, 1F)
                e.isCancelled = true
            }
            if (b != null) {
                if ((p.location.distance(b.location) >= 2) and (b.type == Material.BARREL)) {
                    p.sendMessage("§3§l  Ruins §8≫ §7가까이서 열어야 할 것 같다")
                    e.isCancelled = true
                }
            }
        }

    }

}