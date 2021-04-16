package com.bins.ruins.call.events

import com.bins.ruins.custom.StoneFileBreakEvent
import com.bins.ruins.utilities.Util.bb
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener


class EvntStoneFile: Listener{
    @EventHandler
    fun event(e : StoneFileBreakEvent){
        bb("hi!")
        val b = e.block
        for(block in getNearbyBlocks(b, 3)){
            if((block.type == Material.COBBLESTONE) and (EvntBlockBreak.isDetectAir(b))) {
                block.type = Material.MOSSY_COBBLESTONE

                return
            }
        }
    }
/*
function nearbyBlocks(b: Block, radius: Integer): Block:
    loop blocks between location({_b}'s location's x-coord + {_radius}, {_b}'s location's y-coord,({_b}'s location's z-coord + {_radius})) and location({_b}'s location's x-coord - {_radius}, {_b}'s location's y-coord,({_b}'s location's z-coord - {_radius})):
        if loop-value is not air:
            add loop-value to {_list}
    return {_list}
*/
    private fun getNearbyBlocks(b: Block, radius: Int): List<Block> {
        val blocks: MutableList<Block> = ArrayList()
        for (x in b.location.blockX - radius..b.location.blockX + radius) {
            for (y in b.location.blockY - radius..b.location.blockY + radius) {
                for (z in b.location.blockZ - radius..b.location.blockZ + radius) {
                    if(b.location.world.getBlockAt(x, y, z) != b)
                        if(!b.location.world.getBlockAt(x, y, z).isEmpty)
                            blocks.add(b.location.world.getBlockAt(x, y, z))
                }
            }
        }
        return blocks
    }
}