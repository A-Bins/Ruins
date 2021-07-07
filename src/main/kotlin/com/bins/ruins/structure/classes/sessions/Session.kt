package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.structure.interfaces.session.SessionInfo
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import java.util.*

class Session(var state: SessionState = SessionState.READY) {

    var sessionKey: SessionKey = SessionKey.Empty
        private set
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
    fun off() {
        sessionKey = SessionKey.Empty
        state = SessionState.READY
    }
}