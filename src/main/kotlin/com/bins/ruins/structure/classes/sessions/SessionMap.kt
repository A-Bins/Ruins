package com.bins.ruins.structure.classes.sessions

sealed class SessionMap {
    val first = Session()
    val second = Session()
    val third = Session()
    val fourth = Session()
    object StreetOfAbin: SessionMap()

}