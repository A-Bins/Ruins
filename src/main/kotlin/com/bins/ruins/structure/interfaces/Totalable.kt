package com.bins.ruins.structure.interfaces

import kotlin.math.round
import kotlin.math.roundToLong

interface Totalable {
    var k: Int
    var d: Int
    val ratio: Double
        get() = round(k.toDouble() / d.toDouble()*1000)/1000
    val sum: Double
        get() = (k+d).toDouble()
    val toPer: Array<Double>
        get() = arrayOf(round(((k.toDouble() / sum) * 100)*100)/100, round(((d.toDouble() / sum)* 100)*100)/100)
    val toPerRegular: String
        get() = "Kill: ${toPer[0]}%, Death: ${toPer[1]}%"
    val toRegular: String
        get() = "Kill: $k, Death: $d"

    fun add(k: Int, d: Int){
        this.k += k
        this.d += d
    }
    fun clear(){
        this.k = 0
        this.d = 0
    }

}