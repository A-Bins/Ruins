package com.bins.ruins.structure.classes

open class Pages(val pages: Int,val maxSlot: Int) {
    val back = if(pages == 1) null else ((pages-2)*44)+(pages-2)..((pages-1)*44)+(pages-2)
    val current = ((pages-1)*44)+(pages-1)..(pages*44)+(pages-1)
    val next = (pages*44)+(pages)..((pages+1)*44)+(pages)

    fun get(current: Int) = next.toList().indexOf(current)
    fun getB(current: Int) = back!!.toList().indexOf(current)
}