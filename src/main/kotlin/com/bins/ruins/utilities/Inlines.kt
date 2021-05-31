package com.bins.ruins.utilities

object Inlines {
    inline fun <reified T> Any.tryCast(block: T.() -> Unit) {
        if (this is T) {
            block()
        }
    }
    inline fun <reified T> Any.typeCheck() : Boolean = this is T
}