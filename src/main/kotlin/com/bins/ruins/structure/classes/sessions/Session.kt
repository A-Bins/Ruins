package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.Ruins
import com.bins.ruins.structure.classes.sessions.SessionType.*
import com.bins.ruins.structure.enums.defaults.Map
import com.bins.ruins.structure.interfaces.session.SessionInfo
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.collections.ArrayList

sealed class Session(val type: SessionType) {

    private val broken: ArrayList<Pair<Location, Block>> = arrayListOf()
    var state: SessionState = SessionState.READY
        private set
    var sessionKey: SessionKey = SessionKey.Empty
        private set

    var map = Map.EMPTY
    val players: ArrayList<Player> = arrayListOf()


    companion object {
        val Player.session: Session?
            get() = search(this)
        private val sessionNameSpacedKey = NamespacedKey(Ruins.instance,"session")
        private fun search(p: Player): Session? {
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


    fun pile(b: Block) = broken.add(Pair(b.location, b))
    fun rest() = broken.forEach {
        it.first.block.type = it.second.type
        it.first.block.blockData = it.second.blockData
    }
    fun info() {
    }
    fun on(map: Map): SessionInfo<SessionKey.Exist> {
        this.map = map
        sessionKey = SessionKey.Exist()
        state = SessionState.PROGRESS



        return object: SessionInfo<SessionKey.Exist> {
            override val map = this@Session.map
            override val state = this@Session.state
            override val sessionKey = this@Session.sessionKey as SessionKey.Exist
            override val broken = this@Session.broken
            override val players = this@Session.players
        }
    }
    fun off(): SessionInfo<SessionKey.END> {
        if(state != SessionState.PROGRESS) throw IllegalStateException("this Session doesn't in progress")
        sessionKey = SessionKey.END((sessionKey as SessionKey.Exist).id)
        state = SessionState.READY

        val description = object: SessionInfo<SessionKey.END> {
            override val state = this@Session.state
            override val sessionKey = this@Session.sessionKey as SessionKey.END
            override val map = this@Session.map
            override val broken = this@Session.broken
            override val players = this@Session.players
        }


        map = Map.EMPTY
        rest()
        info()




        return description
    }
    class Session1st: Session(FIRST)
    class Session2nd: Session(SECOND)
    class Session3rd: Session(THIRD)
    class Session4th: Session(FOURTH)
}