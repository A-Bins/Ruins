package com.bins.ruins.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import net.minecraft.server.MinecraftServer
import java.util.stream.Collectors

class Header {
    fun header() =
        """§7§m                                                                                             §f
${(((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024)}MB/${((Runtime.getRuntime().maxMemory() / 1024) / 1024)}MB
TPS: ${Math.round(Ruins.instance.server.tps[0]*1000)/1000.0}, ${Math.round(Ruins.instance.server.tps[1]*1000)/1000.0}, ${Math.round(Ruins.instance.server.tps[2]*1000)/1000.0}
            
            
            
            
            
§7§m                                                                                             §f
${Header().player()}
§7§m                                                                                             §f
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
§7§m                                                                                             §f













""".trimIndent()
    private var playerList = ""
    fun player(): String {
        val list = Ruins.players.map { it.name }.chunked(3).toMutableList()
        list.forEach { m ->
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