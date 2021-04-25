package com.bins.ruins.bot

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.Bukkit


class MsgListener: ListenerAdapter() {

    override fun onMessageReceived(e: MessageReceivedEvent) {
        if(e.message.contentRaw.startsWith("벚꽃아 ")) {
            var m = "유저는... "
            for(p in Bukkit.getOnlinePlayers()) {
                m = m.plus("${p.name}\n")
            }
            e.message.reply(m)
        }
    }
}