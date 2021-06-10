package com.bins.ruins.call.events.actions

import com.bins.ruins.call.events.custom.StoneFileBreakEvent
import com.bins.ruins.structure.enums.Farmings
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EvntStoneFile: Listener{
    @EventHandler
    fun event(e : StoneFileBreakEvent){
        val b = e.block
        var use = false
        when(b.type == Material.MOSSY_COBBLESTONE) {
            true -> {
                for (block in getNearbyBlocks(b, 3)) {
                    if(use)
                        continue
                    if ((block.type == Material.COBBLESTONE) and (EvtBlock.isDetectAir(b))) {
                        block.type = Material.MOSSY_COBBLESTONE
                        use = true
                    }
                }
            }
        }
        Farmings.STONE.farming(e.player, b)
    }
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