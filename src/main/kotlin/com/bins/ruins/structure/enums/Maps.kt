package com.bins.ruins.structure.enums

import com.bins.ruins.structure.interfaces.Mappable
import com.bins.ruins.structure.enums.types.RiskType

enum class Maps : Mappable {
    TEST{
        override val index : Int
            get() = 1
        override val mapName : String
            get() = "Test"
        override val risk: RiskType
            get() = RiskType.EASY
    }

}