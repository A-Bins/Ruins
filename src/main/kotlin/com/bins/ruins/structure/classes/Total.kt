package com.bins.ruins.structure.classes

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
    override val percentage: Array<Double>
        get() = super.percentage
    override val perRegular: String
        get() = super.perRegular
    override val sum: Double
        get() = super.sum
    override val regular: String
        get() = super.regular
}