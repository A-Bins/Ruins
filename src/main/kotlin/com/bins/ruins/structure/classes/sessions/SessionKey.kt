package com.bins.ruins.structure.classes.sessions

import java.util.*

sealed class SessionKey {
    object Empty: SessionKey()
    class Exist(val id: UUID = UUID.randomUUID()): SessionKey() {

    }
    class END(val wasId: UUID): SessionKey() {

    }
}