package com.bins.ruins.structure.objects

import com.bins.ruins.Ruins
import com.bins.ruins.structure.classes.Header
import org.bukkit.entity.Player
import java.util.stream.Collectors

class env {
    companion object {
        val STASH_VALUE: Array<Int> = arrayOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25)

        const val ENABLE_INFO: String = """
            루인스 플러그인 활성화!
            
            
                       * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                       *                                                     *
                       *               Ruins 플러그인 지침                   *
                       *                                                     *
                       *   이 플러그인은 A_bins(Bins#1004)가 만들었으며      *
                       *  Ruins 서버의 주 코어부분의 플러그인임을 알립니다.  *
                       *                                                     *
                       *          기획 : DDang_    제작 : A_bins             *
                       *                                                     *
                       *                                                     *
                       *   < Copyright 2021. Ruins. All rights reserved. >   *
                       *                                                     *
                       * * * * * * * * * * * * * * * * * * * * * * * * * * * *
      
      
      
        """

        const val BOT_TOKEN = "ODM1MTA1MjM2MTcwODMzOTMw.YIKmWw.jIAUK09pe89TxtJyCAr2-BTrxHs"
    }
}