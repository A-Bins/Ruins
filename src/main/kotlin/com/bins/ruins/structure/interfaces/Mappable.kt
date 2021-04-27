package com.bins.ruins.structure.interfaces

import com.bins.ruins.structure.RiskType

interface Mappable {
    val index : Int
    val worldName : String
    val risk : RiskType
}