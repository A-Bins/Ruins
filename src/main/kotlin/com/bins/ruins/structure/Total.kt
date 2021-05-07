package com.bins.ruins.structure

import com.bins.ruins.structure.interfaces.Totalable

class Total private constructor(override var k: Int, override var d: Int) : Totalable{
    companion object{
        fun create(k: Int, d: Int): Total = Total(k, d)
    }
    fun matchTotal(that: Total): Boolean{
        return ratio > that.ratio
    }

    override val ratio: Double
        get() = super.ratio
    override val toPer: Array<Double>
        get() = super.toPer
    override val toPerRegular: String
        get() = super.toPerRegular
    override val sum: Double
        get() = super.sum
    override val toRegular: String
        get() = super.toRegular
}