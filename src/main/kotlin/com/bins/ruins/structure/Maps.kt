package com.bins.ruins.structure

import com.bins.ruins.structure.interfaces.Mappable
import com.bins.ruins.types.RiskType

enum class Maps : Mappable {
    TEST{
        override val index : Int
            get() = 1
        override val worldName : String
            get() = "Test"
        override val risk: RiskType
            get() = RiskType.EASY
    }

}