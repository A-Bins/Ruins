package com.bins.ruins.structure.interfaces

interface Totalable {
    var k: Int
    var d: Int
    var a: Int
    val ratio: Double
        get() = (k.toDouble()+d.toDouble()) / d.toDouble()
    val sum: Double
        get() = (k+d+a).toDouble()
    val toPer: Array<Double>
        get() = arrayOf((k.toDouble() / sum)/100, (d.toDouble() / sum)/100, (a.toDouble() / sum)/100)
    val toPerRegular: String
        get() = "Kill: ${toPer[0]}%, Death: ${toPer[1]}%, Assist: ${toPer[2]}%"
    val toRegular: String
        get() = "Kill: $k, Death: $d, Assist: $a"

    fun add(k: Int, d: Int, a: Int){
        this.k += k
        this.d += d
        this.a += a
    }
    fun clear(){
        this.k = 0
        this.d = 0
        this.a = 0
    }

}