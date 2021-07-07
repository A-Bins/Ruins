package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.Ruins
import com.bins.ruins.structure.enums.defaults.Map
import com.bins.ruins.structure.interfaces.session.SessionInfo
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.*

class Session {
    companion object {
        private val sessionNameSpacedKey = NamespacedKey(Ruins.instance,"session")
        fun search(p: Player): Session? {
            val session = p.persistentDataContainer[sessionNameSpacedKey, PersistentDataType.STRING]!!
            //어빈가|1
            val map = Map.valueOf(session.split("|")[0])
            return when(session.split("|")[1].toInt()){
                1 -> SessionMap.valueMapOf(map.mapName)!!.first
                2 -> SessionMap.valueMapOf(map.mapName)!!.second
                3 -> SessionMap.valueMapOf(map.mapName)!!.third
                4 -> SessionMap.valueMapOf(map.mapName)!!.fourth
                else -> null
            }
        }
    }
    val broken: ArrayList<Pair<Location, Block>> = arrayListOf()
    val players: ArrayList<Player> = arrayListOf()
    var state: SessionState = SessionState.READY
        private set
    var sessionKey: SessionKey = SessionKey.Empty
        private set
    fun rest() = broken.forEach {
        it.first.block.type = it.second.type
        it.first.block.blockData = it.second.blockData
    }
    fun info() {

    }
    fun on(): SessionInfo<SessionKey.Exist> {
        sessionKey = SessionKey.Exist()
        state = SessionState.PROGRESS



        return object: SessionInfo<SessionKey.Exist> {
            override val state: SessionState
                get() = this@Session.state
            override val sessionKey: SessionKey.Exist
                get() = this@Session.sessionKey as SessionKey.Exist
        }
    }
    fun off(): SessionInfo<SessionKey.END> {
        sessionKey = SessionKey.END()
        state = SessionState.READY
        rest()
        info()




        return object: SessionInfo<SessionKey.END> {
            override val state: SessionState
                get() = this@Session.state
            override val sessionKey: SessionKey.END
                get() = this@Session.sessionKey as SessionKey.END
        }
    }
}