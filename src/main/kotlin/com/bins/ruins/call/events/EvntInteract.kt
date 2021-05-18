package com.bins.ruins.call.events

import com.bins.ruins.Ruins
import com.bins.ruins.run.Vars
import com.bins.ruins.utilities.Util.bb
import com.bins.ruins.utilities.Util.isGiven
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue

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
                val value = Vars.glowValue[p.uniqueId]!!
                if(!value.isOnGround)
                    return
                if(value.hasMetadata("given"))
                    if (value.getMetadata("given")[0].asBoolean())
                        return
                e.isCancelled = true
                val given = isGiven(p, value.itemStack)
                when(given == -1){
                    true -> {
                        value.setMetadata("given", FixedMetadataValue(Ruins.instance, true))
                        value.remove()
                        p.inventory.addItem(value.itemStack)
                        Vars.glowValue[p.uniqueId] = null
                        p.playSound(p.location, Sound.ENTITY_ITEM_PICKUP, 1F, 1F)
                    }
                    false -> {
                        value.setMetadata("given", FixedMetadataValue(Ruins.instance, true))
                        value.remove()
                        val gave = Vars.glowValue[p.uniqueId]!!.itemStack.clone().apply {
                            amount -= given
                        }
                        val drop = Vars.glowValue[p.uniqueId]!!.itemStack.clone().apply {
                            amount -= gave.amount
                        }
                        bb("gave ${gave.amount}          drop ${drop.amount}")
                        p.inventory.addItem(gave)
                        p.world.dropItemNaturally(value.location, drop)

                        Vars.glowValue[p.uniqueId] = null
                        p.playSound(p.location, Sound.ENTITY_VILLAGER_NO, 1F, 1F)
                    }
                }

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