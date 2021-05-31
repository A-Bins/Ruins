package com.bins.ruins.structure.classes

import com.bins.ruins.run.Vars.strashs
import com.bins.ruins.utilities.Util.bb
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList

class Strash private constructor(val uuid: UUID, vararg val drawers: Drawer) {

    companion object{
        fun create(uuid: UUID, vararg drawers: Drawer): Strash = Strash(uuid, *drawers)
    }
    val unlockProgress: Int = drawers.size
    init {
        for((i, dr) in drawers.withIndex()) {
            if (dr.items.size > 54){
                throw IllegalArgumentException("Drawer in array of ItemStack sizes is over 55 got ${dr.items.size}, from index of $i, and player's uuid $uuid")
                break
            }
        }
        if(unlockProgress > 14){
            throw IllegalArgumentException("UnlockProgress is over 15 got $unlockProgress, and player's uuid $uuid")
        }
    }

}