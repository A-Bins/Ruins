package com.bins.ruins.structure.classes

import com.bins.ruins.env
import com.bins.ruins.run.vars.stashes
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Suppress("DEPRECATION")
class Stash private constructor(val uuid: UUID, vararg val drawers: Drawer) {

    companion object{


        val Player.stash: Stash?
            get() = stashes[this.uniqueId]
        fun Player.drawerSave(){
            fun Int.hasDrawer(): Boolean{
                if(this > 14) return false
                if(this@drawerSave.stash == null) this@drawerSave.kickPlayer("u don't have strash variables plz Rejoin")
                return this@drawerSave.stash!!.drawers[this].unlocked
            }
            if(!openInventory.title.contains("([0-9])번 보관함".toRegex())) return
            val i = (openInventory.title.replace("번 보관함", "")).toInt()
            if(!(i-1).hasDrawer()) return
            Bukkit.broadcastMessage("${i-1}")
            for((k, v) in openInventory.topInventory.contents.withIndex()){
                if(k / 8 < stash!!.drawers[i-1].unlockState) {

                    Bukkit.broadcastMessage("$k, $v, ${openInventory.title}")
                    stash!!.drawers[i-1].items[k] = v
                }
            }



        }
        fun Player.inDrawers(i: Int){
            fun Int.hasDrawer(): Boolean{
                if(this > 14) return false
                if(this@inDrawers.stash == null) this@inDrawers.kickPlayer("u don't have strash variables plz Rejoin")
                return this@inDrawers.stash!!.drawers[this].unlocked
            }
            if(this.openInventory.title != "물품 보관함") return
            if(!(i-1).hasDrawer()) return
            openInventory(
                Bukkit.createInventory(null, 9*6, "${i}번 보관함").apply {
                    val removes = arrayOf(4, 13, 22, 31, 40, 49)
                    val it = stash!!.drawers[i-1]
                    removes.toList().withIndex().toList().stream().filter { (index, _) -> index+1 > it.unlockState }.forEach { (k, v) ->
                            setItem(v, ItemStack(Material.ANVIL).apply {
                                val meta = itemMeta
                                meta.setDisplayName("§7${k+1}번 줄이 잠금되어 있어요!")
                                itemMeta = meta
                            })
                            arrayOf(v-4, v-3, v-2, v-1, v+1, v+2, v+3, v+4).forEach { w ->
                                setItem(w, ItemStack(Material.IRON_BARS).apply {
                                    val meta = itemMeta
                                    meta.setDisplayName("§7§o이 줄은 잠금 되어 있습니다..")
                                    itemMeta = meta
                                })
                            }
                    }

                    Bukkit.broadcastMessage("${i-1}")
                    for( (k, v) in it.items) {
                        Bukkit.broadcastMessage("$k, $v, ${openInventory.title}")
                        setItem(k, v)
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
                            meta.setDisplayName("${i+1}")
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
            val drawers: ArrayList<Drawer> = arrayListOf()
            for(i in 0..13){
                drawers.add(Drawer(HashMap(), unlockState = 1, unlocked = true))
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
            throw IllegalArgumentException("progress is over 15 got $progress, and player's uuid $uuid")
        }
    }

}