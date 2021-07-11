package com.bins.ruins.structure.classes

import java.util.*
import kotlin.math.round

data class Total private constructor(var k: Int, var d: Int){
    companion object{
        fun create(k: Int, d: Int): Total = Total(k, d)
    }
    fun matchTotal(that: Total): Boolean{
        return ratio > that.ratio
    }

    override fun toString() = "Kill : $k, Death : $d"

    val ratio: Double
        get() = round(k.toDouble() / d.toDouble()*1000) /1000
    val sum: Double
        get() = (k+d).toDouble()
    val percentage: Array<Double>
        get() = arrayOf(round(((k.toDouble() / sum) * 100)*100) /100, round(((d.toDouble() / sum)* 100)*100) /100)
    val perRegular: String
        get() = "Kill: ${percentage[0]}%, Death: ${percentage[1]}%"
    val regular: String
        get() = "Kill: $k, Death: $d"

    operator fun plus(t: Total) = create(k = k, d = d).apply {
        k += t.k
        d += t.d
    }
    operator fun plusAssign(t: Total)  {
        k += t.k
        d += t.d
    }
    operator fun minus(t: Total) = create(k = k, d = d).apply {
        k -= t.k
        d -= t.d
    }
    operator fun minusAssign(t: Total)  {
        k -= t.k
        d -= t.d
    }
    fun clear(){
        k = 0
        d = 0
    }
}