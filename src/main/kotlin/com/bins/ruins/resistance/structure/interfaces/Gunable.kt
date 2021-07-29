package com.bins.ruins.resistance.structure.interfaces

import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Mode
import com.bins.ruins.resistance.structure.enums.RecoilPattern
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Sound


interface Gunable {
    val color: Color
    val displayName: String
    val description: Array<String>
    val ammo: Ammo
    val recoilPattern: RecoilPattern
    val hipAccuracy: Double
    val pointAccuracy: Double
    val sound: HashMap<Sound, Pair<Float, Long>>
    val rpm: Double
    val damage: Double
    val shootSpeed: Int
    val magazine: Int
    val recoilVertical: Float
    val recoilHorizontal: Float
    val maxRange: Int
    val okRange: Int
    val ignoreBlock: Array<Material>
    val mode: Mode
    //ALIGHT?
}