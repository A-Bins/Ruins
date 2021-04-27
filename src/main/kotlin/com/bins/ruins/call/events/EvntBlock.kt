package com.bins.ruins.call.events

import com.bins.ruins.custom.StoneFileBreakEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class EvntBlock: Listener {
    companion object {
        fun isDetectAir(block: Block): Boolean {
        val loc = block.location

        val a = loc.clone()
        a.x += 1
        val b = loc.clone()
        b.x -= 1

        val c = loc.clone()
        c.z += 1
        val d = loc.clone()
        d.z -= 1
        val e = loc.clone()

        e.y += 1
        val f = loc.clone()
        f.y -= 1
        val list: List<Location> = listOf(a, b, c, e, f)
        for (l in list) {
            if (l.block.isEmpty) {
                return true
            }
        }
        return false
    }
    }

    @EventHandler
    fun event2(e: BlockPlaceEvent){
        if(e.player.isOp)
            return
        e.isCancelled = true
    }
    @EventHandler
    fun event(e: BlockBreakEvent){
        val b = e.block
        if(!((b.type == Material.MOSSY_COBBLESTONE)or(b.type == Material.COBBLESTONE)))
            return

        if(!isDetectAir(b))
            return


        Bukkit.getPluginManager().callEvent(StoneFileBreakEvent(e.player,e.block,e))
    }
}