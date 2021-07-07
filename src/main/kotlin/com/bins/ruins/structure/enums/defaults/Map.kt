package com.bins.ruins.structure.enums.defaults

import com.bins.ruins.structure.interfaces.defaults.Mappable
import com.bins.ruins.structure.enums.types.Risk

enum class Map : Mappable {

    STREET_OF_ABIN{
        override fun toString(): String = mapName
        override val index : Int
            get() = 0
        override val mapName : String
            get() = "어빈가"
        override val risk: Risk
            get() = Risk.EASY
    };

}