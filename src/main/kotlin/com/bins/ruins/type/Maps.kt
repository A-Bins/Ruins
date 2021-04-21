package com.bins.ruins.type

enum class Maps : Mappable{
    TEST{
        override val index : Int
            get() = 1
        override val worldName : String
            get() = "Test"
        override val risk: RiskType
            get() = RiskType.EASY
    }

}