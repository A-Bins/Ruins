package com.bins.ruins.structure.classes

import com.bins.ruins.env
import com.bins.ruins.run.vars.strashs
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class Strash private constructor(val uuid: UUID, vararg val drawers: Drawer) {

    companion object{


        val Player.strash: Strash?
            get() = strashs[this.uniqueId]

        fun Player.inStrash(){
            fun Int.hasDrawer(): Boolean{
                if(this > 13) return false
                if(this@inStrash.strash == null) return false
                return this@inStrash.strash!!.drawers[this].unlocked
            }
            val inventory = Bukkit.createInventory(null, 4*9, "물품 보관함").apply {
                for((i, index) in env.STRASH_VALUE.withIndex()){
                    setItem(index, ItemStack(if(i.hasDrawer()) Material.CHEST else Material.IRON_BARS).apply {
                        val meta = itemMeta
                        meta.setDisplayName("$i")
                        itemMeta = meta

                    })
                }
            }
            openInventory(inventory)
        }


        fun create(uuid: UUID, vararg drawers: Drawer) {
            strashs[uuid] = Strash(uuid, *drawers)
        }
        fun default(uuid: UUID){
            if(strashs.containsKey(uuid)) return
            val itemstacks: Array<ItemStack> = arrayOf()
            val drawers: ArrayList<Drawer> = arrayListOf()
            for(i in 1..13){
                drawers.add(Drawer(*itemstacks, unlockState = 1, unlocked = true))
            }


            strashs[uuid] = Strash(
                uuid,
                *drawers.toTypedArray()
            )

        }
    }
    val progress = drawers.size
    init {
        for((i, dr) in drawers.withIndex()) {
            if (dr.items.size > 54){
                throw IllegalArgumentException(
                    "Drawer in array of size of Itemstack got over 55 ${dr.items.size}, from index of $i and from player's uuid $uuid"
                )
                break
            }
        }
        if(progress > 14){
            throw IllegalArgumentException("UnlockProgress is over 15 got $progress, and player's uuid $uuid")
        }
    }

}