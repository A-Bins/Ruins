package com.bins.ruins.structure.classes

open class Pages(pages: Int, maxSlot: Int) {
    val back =
        if (pages == 1)
            null
        else ((pages-2)*maxSlot..(pages-1)*maxSlot).toList()
    val current = ((pages-1)*maxSlot+1..pages*maxSlot+1).toList()
    val next = (pages*maxSlot+2..(pages+1)*maxSlot+2).toList()
    fun get(): Triple<List<Int>?, List<Int>, List<Int>> {
        return Triple(back, current, next)
    }
}