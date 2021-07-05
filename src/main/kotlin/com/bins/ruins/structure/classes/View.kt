package com.bins.ruins.structure.classes

import com.bins.ruins.structure.objects.vars
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
@Suppress("DEPRECATION")
class View(private val p: Player){
    companion object{
        val cancels: ArrayList<String> = ArrayList()
        val views: ArrayList<String> = ArrayList()
        private fun create(lines: Int, title: String): Inventory{
            if(!views.contains(title))
                views.add(title)
            return Bukkit.createInventory(null, lines, title)
        }
    }
    fun inContainer() : InventoryView {
        return p.openInventory(asContainer)!!
    }
    val asContainer = create(9, "컨테이너").also { Inv ->
        val ib = ItemStack(Material.IRON_BARS).apply {
            val meta = itemMeta
            meta.setDisplayName("§f")
            itemMeta = meta
        }
        Inv.setItem(0, ib)
        Inv.setItem(1, ib)
        Inv.setItem(2, ib)
        Inv.setItem(3, ib)
        Inv.setItem(4, vars.container[p.uniqueId])
        Inv.setItem(5, ib)
        Inv.setItem(6, ib)
        Inv.setItem(7, ib)
        Inv.setItem(8, ib)
    }
}