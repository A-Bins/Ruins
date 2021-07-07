package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.structure.enums.defaults.Map

sealed class SessionMap {
    companion object {
        fun valueMapOf(mapName: String) = arrayOf(StreetOfAbin).find { it.map.mapName == mapName }
    }
    val first = Session()
    val second = Session()
    val third = Session()
    val fourth = Session()
    object StreetOfAbin: SessionMap() {
        val map = Map.STREET_OF_ABIN
    }

}