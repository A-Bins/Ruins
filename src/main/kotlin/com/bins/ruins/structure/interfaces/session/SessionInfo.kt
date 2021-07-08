package com.bins.ruins.structure.interfaces.session

import com.bins.ruins.structure.classes.sessions.SessionKey
import com.bins.ruins.structure.classes.sessions.SessionState
import com.bins.ruins.structure.enums.defaults.Map
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

interface SessionInfo<out T: SessionKey> {
    val map: Map
    val state: SessionState
    val sessionKey: T
    val players: ArrayList<Player>
    val broken: java.util.ArrayList<Pair<Location, Block>>
}