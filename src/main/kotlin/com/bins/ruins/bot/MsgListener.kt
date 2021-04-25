package com.bins.ruins.bot

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.Bukkit

class MsgListener: ListenerAdapter() {

    override fun onMessageReceived(e: MessageReceivedEvent){
        if(e.message.contentRaw.startsWith("벚꽃아 ")) {
            when(e.message.contentRaw.replace("벚꽃아 ", "")){
                "유저" -> {
                    var m = "유저는... ";
                    for (p in Bukkit.getOnlinePlayers()) {
                        m = "$m ${p.name}, "
                    }
                    m = m.replace("(,\\s)$".toRegex(), "");
                    e.message.reply(m).queue();
                }
                "핑" -> {
                    val times = System.currentTimeMillis();

                    e.message.reply("퐁! "+((System.currentTimeMillis().toDouble() - times.toDouble()))+"초!").queue();
                }
            }
        }

    }
}