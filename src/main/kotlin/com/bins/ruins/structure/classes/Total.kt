package com.bins.ruins.structure.classes

import java.util.*
import kotlin.math.round

class Total private constructor(var k: Int, var d: Int){
    companion object{
        fun create(k: Int, d: Int): Total = Total(k, d)
    }
    fun matchTotal(that: Total): Boolean{
        return ratio > that.ratio
    }

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

    fun add(k: Int, d: Int){
        this.k += k
        this.d += d
    }
    fun clear(){
        this.k = 0
        this.d = 0
    }
}