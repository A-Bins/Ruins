package com.bins.ruins

import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.gateway.DisconnectEvent
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.concurrent.thread

//        Ruins.cherryBlossom.on<DisconnectEvent.DiscordCloseEvent> {
//            kord.rest.channel.createMessage(Snowflake("835114682871578624")){
//                content = "벚꽃봇 잔다.."
//            }
//        }
object CherryBlossom {

    /*

    관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝
    관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝
    관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝
    관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝
    관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝 관 짝
    @DelicateCoroutinesApi
    fun cherryBlossomInitializedAsync() = GlobalScope.async {
        Ruins.cherryBlossom = Kord(env.BOT_TOKEN)
        Ruins.cherryBlossom.login {
            playing("${Ruins.instance.server.onlinePlayers.size}명이 Ruins를 플레이")
            Ruins.scheduler.runTaskTimerAsynchronously(Ruins.instance, Runnable {
                GlobalScope.async {
                    Ruins.cherryBlossom.editPresence {
                        playing("${Ruins.instance.server.onlinePlayers.size}명이 Ruins를 플레이")
                    }
                }.start()
            }, 0, 1)
        }

        Ruins.cherryBlossom.on<ReadyEvent> {
            kord.rest.channel.createMessage(Snowflake("835114682871578624")){
                content = "벚꽃봇 일어나따!"
            }
        }
    }.start()
    */

}