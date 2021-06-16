package com.bins.ruins.structure.interfaces.defaults

import com.bins.ruins.structure.enums.types.Risk

interface Mappable {
    val index : Int
    val mapName : String
    val risk : Risk
}