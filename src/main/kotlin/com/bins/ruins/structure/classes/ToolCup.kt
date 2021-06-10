package com.bins.ruins.structure.classes

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.IllegalStateException
import java.util.*
import java.util.stream.Collectors
import javax.xml.stream.Location
import kotlin.collections.ArrayList

class ToolCup private constructor(val location: Location, val owner: UUID, val resources: HashMap<String, Int>) {
    init {
        if (toolCups.contains(this)) throw IllegalStateException("ToolCup is already exist from $location, owner: $owner")

    }
    companion object {
        fun UUID.hasToolCup() = toolCups.stream().anyMatch {
            it.owner == this
        }

        fun UUID.findToolCup(): Location? {
            if (!hasToolCup()) return null
            return toolCups.stream().filter { it.owner != this }.collect(Collectors.toList())[0].location
        }
        val toolCups: ArrayList<ToolCup> = arrayListOf()
        fun create(location: Location, owner: UUID, resources: HashMap<String, Int>) = ToolCup(location, owner, resources).also {
            toolCups.add(it)
            return it
        }


    }
}