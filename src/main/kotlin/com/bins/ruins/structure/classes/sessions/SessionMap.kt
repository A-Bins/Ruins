package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.structure.enums.defaults.Map

sealed class SessionMap {

    companion object {
        fun valueMapOf(mapName: String) = arrayOf(StreetOfAbin).find { it.map.mapName == mapName }
    }
    val first = Session.Session1st()
    val second = Session.Session2nd()
    val third = Session.Session3rd()
    val fourth = Session.Session4th()
    object StreetOfAbin: SessionMap() {
        val map = Map.STREET_OF_ABIN
    }

}