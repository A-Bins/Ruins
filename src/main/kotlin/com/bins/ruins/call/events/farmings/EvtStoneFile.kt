package com.bins.ruins.call.events.farmings

import com.bins.ruins.call.events.actions.EvtBlock
import com.bins.ruins.call.events.custom.StoneFileBreakEvent
import com.bins.ruins.structure.enums.defaults.Farming
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EvtStoneFile: Listener{
    @EventHandler
    fun event(e : StoneFileBreakEvent){
        val b = e.block
        when(b.type == Material.MOSSY_COBBLESTONE) {
            true -> {
                val random = nearByBlocks(b, 5).random()
                if (random.type == Material.COBBLESTONE && EvtBlock.isDetectAir(b)) {
                    random.type = Material.MOSSY_COBBLESTONE
                }
            }
        }
        Farming.STONE.farming(e.player, b)
    }
    fun nearByBlocks(b: Block, radius: Int): MutableList<Block> {
        val blocks: MutableList<Block> = ArrayList()
        for (x in b.location.blockX - radius..b.location.blockX + radius) {
            for (y in b.location.blockY - radius..b.location.blockY + radius) {
                for (z in b.location.blockZ - radius..b.location.blockZ + radius) {
                    if (b.location.world!!.getBlockAt(x, y, z) != b)
                        if (!b.location.world!!.getBlockAt(x, y, z).isEmpty)
                            blocks.add(b.location.world!!.getBlockAt(x, y, z))
                }
            }
        }
        return blocks
    }
}