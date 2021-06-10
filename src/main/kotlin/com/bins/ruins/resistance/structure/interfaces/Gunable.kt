package com.bins.ruins.resistance.structure.interfaces

import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Mode
import org.bukkit.Material


interface Gunable {
    val displayName: String
    val description: Array<String>
    val ammo: Ammo
    val burstSpeed: Int
    val shootSpeed: Double
    val magazine: Int
    val recoilVertical: Float
    val recoilHorizontal: Float
    val maxRange: Int
    val okRange: Int
    val ignoreBlock: Array<Material>
    val mode: Mode
    //ALIGHT?
}