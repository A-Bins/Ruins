package com.bins.ruins.structure.interfaces

import com.bins.ruins.types.RiskType

interface Mappable {
    val index : Int
    val worldName : String
    val risk : RiskType
}