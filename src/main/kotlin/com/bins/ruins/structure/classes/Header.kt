package com.bins.ruins.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import java.util.stream.Collectors

class Header {
    fun header() =
        """§7§m                                                                                             §f

            
            
            
            
            
            
§7§m                                                                                             §f
${Header().player()}
§7§m                                                                                             §f
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
§7§m                                                                                             §f    













""".trimIndent()
    private var playerList = ""
    fun player(): String {
        Ruins.players.map { it.name }.chunked(3).toMutableList().forEach { m ->
           run {
               var s = ""
               m.forEach a@{

                   var string = it

                   if(m.last() == it) {
                       for (i in 1..((93 - s.length - it.length) - 25)) {
                           string += " "
                       }
                       s += string
                       for (i in 1..25) {
                           s = " $s"
                       }
                       playerList += "$s${if(m.indexOf(it) == 2) "\n" else ""}"
                       return@run
                   }
                   for(i in 1..(19-it.length)) {
                       string += " "
                   }
                   s += string
               }
               playerList += s
           }
        }
        return playerList
    }
}