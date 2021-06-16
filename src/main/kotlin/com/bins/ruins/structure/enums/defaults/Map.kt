package com.bins.ruins.structure.enums.defaults

import com.bins.ruins.structure.interfaces.defaults.Mappable
import com.bins.ruins.structure.enums.types.Risk

enum class Map : Mappable {
    TEST{
        override val index : Int
            get() = 1
        override val mapName : String
            get() = "Test"
        override val risk: Risk
            get() = Risk.EASY
    }

}