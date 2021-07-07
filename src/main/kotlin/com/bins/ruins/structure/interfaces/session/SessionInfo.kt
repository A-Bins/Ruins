package com.bins.ruins.structure.interfaces.session

import com.bins.ruins.structure.classes.sessions.SessionKey
import com.bins.ruins.structure.classes.sessions.SessionState

interface SessionInfo<out T: SessionKey> {
    val state: SessionState
    val sessionKey: T
}