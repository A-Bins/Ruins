package com.bins.ruins.structure.classes.sessions

import com.bins.ruins.structure.objects.utilities.Receiver.bb
import java.util.*

class Session(var state: SessionState = SessionState.READY) {

    var sessionKey: SessionKey = SessionKey.Empty
        private set
    fun on() {
        sessionKey = SessionKey.Exist()
        state = SessionState.PROGRESS

        when(sessionKey) {
            is SessionKey.Exist -> {
            }
            SessionKey.Empty -> throw IllegalStateException("Sessionkey is Empty")

        }


    }
    fun off() {
        sessionKey = SessionKey.Empty
        state = SessionState.READY
    }
}