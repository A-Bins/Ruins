package com.bins.ruins.structure.classes

import org.bukkit.event.inventory.InventoryClickEvent
import java.security.SecureRandom
import java.util.*

class Flea(page: Int): Pages(page,44) {
    companion object{
        fun InventoryClickEvent.fleaCondition() = view.title.contains("암시장")
        fun InventoryClickEvent.flea() {
            val page = view.title.substring(view.title.indexOf("페")-1).toInt()
            val flea = Flea(page)
        }
    }

}