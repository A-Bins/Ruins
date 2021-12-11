package com.bins.ruins.call.events.actions

import com.bins.ruins.Ruins
import com.bins.ruins.structure.enums.items.medicals.Syringe.Companion.isSyringe
import com.bins.ruins.structure.objects.vars
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import com.bins.ruins.structure.objects.utilities.Util.Companion.isGiven
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.metadata.FixedMetadataValue

class EvtInteract : Listener{
    @EventHandler
    fun event(e: PlayerInteractEvent){
        val b = e.clickedBlock
        val p = e.player
        if(e.hand != EquipmentSlot.HAND) return

        if((e.action == Action.RIGHT_CLICK_AIR) or (e.action == Action.RIGHT_CLICK_BLOCK)) {
            if (vars.glowValue[p.uniqueId] != null) {
                val value = vars.glowValue[p.uniqueId]!!
                if(!value.isOnGround)
                    return
                if(value.hasMetadata("given"))
                    if (value.getMetadata("given")[0].asBoolean())
                        return
                e.isCancelled = true
                val given = isGiven(p, value.itemStack)
                when(given == -1) {
                    true -> {
                        value.setMetadata("given", FixedMetadataValue(Ruins.instance, true))
                        value.remove()
                        p.inventory.addItem(value.itemStack)
                        vars.glowValue[p.uniqueId] = null
                        p.playSound(p.location, Sound.ENTITY_ITEM_PICKUP, 1F, 1F)
                    }
                    false -> {
                        value.setMetadata("given", FixedMetadataValue(Ruins.instance, true))
                        value.remove()
                        val gave = vars.glowValue[p.uniqueId]!!.itemStack.clone().apply {
                            amount -= given
                        }
                        val drop = vars.glowValue[p.uniqueId]!!.itemStack.clone().apply {
                            amount -= gave.amount
                        }
                        p.inventory.addItem(gave)
                        p.world.dropItemNaturally(value.location, drop)

                        vars.glowValue[p.uniqueId] = null
                        p.playSound(p.location, Sound.ENTITY_VILLAGER_NO, 1F, 1F)
                    }
                }

            }
            else if (e.hasItem()) {
                if (e.item!!.isSyringe) {
                    e.item!!.clone()
                }
            }
            else if (b != null) {
                when {
                    (p.location.distance(b.location) >= 2) and (b.type == Material.BARREL) -> {
                        p.sendMessage("§3§l  Ruins §8≫ §7가까이서 열어야 할 것 같다")
                        e.isCancelled = true
                    }
                }
            }
        }
        else if(!p.isOp){
            e.isCancelled = true
            return
        }

    }

}