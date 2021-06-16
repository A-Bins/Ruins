package com.bins.ruins.structure.interfaces.defaults

import com.bins.ruins.structure.enums.types.RiskType

interface Mappable {
    val index : Int
    val mapName : String
    val risk : RiskType
}