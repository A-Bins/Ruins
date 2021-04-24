package com.bins.ruins.bot

import com.bins.ruins.Ruins
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter


class MsgListener: ListenerAdapter() {

    override fun onMessageReceived(e: MessageReceivedEvent) {
        if(e.message.contentDisplay.startsWith("벚꽃아 ")){
            val msg = e.message.contentDisplay.replace("벚꽃아", "")
            when(msg){
                "핑" -> e.message.reply("퐁!")
                "유저", "유저들" -> {
                    val m = ""
                    for(p in Ruins.instance.server.onlinePlayers) {
                        m.plus("${p.name}\n")
                    }
                    e.message.reply(m

                    )
                }
            }
        }
    }
}