package com.bins.ruins.structure.classes

import com.bins.ruins.env
import com.bins.ruins.run.vars.stashes
import com.bins.ruins.structure.classes.Stash.Companion.inStash
import com.bins.ruins.structure.classes.Stash.Companion.stash
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class Stash private constructor(val uuid: UUID, vararg val drawers: Drawer) {

    companion object{


        val Player.stash: Stash?
            get() = stashes[this.uniqueId]
        fun Player.inDrawers(i: Int){
            fun Int.hasDrawer(): Boolean{
                if(this > 13) return false
                if(this@inDrawers.stash == null) this@inDrawers.kickPlayer("u don't have strash variables plz Rejoin")
                return this@inDrawers.stash!!.drawers[this].unlocked
            }
            if(this.openInventory.title != "물품 보관함") return
            if(!i.hasDrawer()) return
            openInventory(
                Bukkit.createInventory(null, 9*6, "${i}번 보관함").apply {
                    val removes = arrayOf(4, 13, 22, 31, 40, 49)
                     stash!!.drawers.toList().stream().forEach {
                         removes.toList().withIndex().toList().stream()
                             .filter { (index, _) -> index+1 > it.unlockState }
                             .forEach {
                                 setItem(it.value, ItemStack(Material.ANVIL).apply {
                                     val meta = itemMeta
                                     meta.setDisplayName("§7${it.index+1}번 줄이 잠금되어 있어요!")
                                     itemMeta = meta
                                 })
                                 arrayOf(
                                     it.value-4,
                                     it.value-3,
                                     it.value-2,
                                     it.value-1,
                                     it.value+1,
                                     it.value+2,
                                     it.value+3,
                                     it.value+4
                                 ).forEach { w ->
                                    setItem(w, ItemStack(Material.IRON_BARS).apply {
                                        val meta = itemMeta
                                        meta.setDisplayName("§7§o이 줄은 잠금 되어 있습니다..")
                                         itemMeta = meta
                                    })
                                }
                            }
                     }
                }
            )
        }
        fun Player.inStash(){
            fun Int.hasDrawer(): Boolean{
                if(this > 13) return false
                if(this@inStash.stash == null) {
                    this@inStash.kickPlayer("u don't have strash variables plz Rejoin")
                    return false
                }
                return this@inStash.stash!!.drawers[this].unlocked
            }
            openInventory(
                Bukkit.createInventory(null, 4 * 9, "물품 보관함").apply {
                    for ((i, index) in env.STASH_VALUE.withIndex()) {
                        setItem(index, ItemStack(if (i.hasDrawer()) Material.CHEST else Material.IRON_BARS).apply {
                            val meta = itemMeta
                            meta.setDisplayName("$i")
                            itemMeta = meta
                        })
                    }
                }
            )
        }
        fun create(uuid: UUID, vararg drawers: Drawer) {
            stashes[uuid] = Stash(uuid, *drawers)
        }
        fun default(uuid: UUID){
            if(stashes.containsKey(uuid)) return
            val itemstacks: Array<ItemStack> = arrayOf()
            val drawers: ArrayList<Drawer> = arrayListOf()
            for(i in 0..13){
                drawers.add(Drawer(*itemstacks, unlockState = 1, unlocked = true))
            }


            stashes[uuid] = Stash(
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