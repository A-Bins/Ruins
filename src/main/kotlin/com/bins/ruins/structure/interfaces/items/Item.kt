package com.bins.ruins.structure.interfaces.items

import org.bukkit.Material

interface Item {
    val name: String
    val description: ArrayList<String>
    val material: Material
}