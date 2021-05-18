package com.bins.ruins.structure.interfaces

import com.bins.ruins.structure.enums.types.RiskType

interface Mappable {
    val index : Int
    val mapName : String
    val risk : RiskType
}