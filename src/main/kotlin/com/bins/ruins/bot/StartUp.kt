package com.bins.ruins.bot

import com.bins.ruins.env
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

object StartUp {
    fun startBot(): JDA {
        val builder = JDABuilder.createDefault(env.BOT_TOKEN)
        builder.setActivity(Activity.playing("Ruins"))
        builder.addEventListeners(MsgListener())
        return builder.build()
    }
}