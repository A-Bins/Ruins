package com.bins.ruins

import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.Ruins.Companion.rtAsync
import com.bins.ruins.structure.classes.Header
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.cache.data.EmbedFooterData
import dev.kord.core.entity.Embed
import dev.kord.core.event.gateway.DisconnectEvent
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.entity.Player
import kotlin.concurrent.thread

//        Ruins.cherryBlossom.on<DisconnectEvent.DiscordCloseEvent> {
//            kord.rest.channel.createMessage(Snowflake("835114682871578624")){
//                content = "벚꽃봇 잔다.."
//            }
//        }
class CherryBlossom {
    companion object {
        @DelicateCoroutinesApi
        fun cherryBlossomLogoutAsync() = GlobalScope.async {
            Ruins.cherryBlossom.logout()
        }
        @DelicateCoroutinesApi
        fun cherryBlossomInitializedAsync() = GlobalScope.async {
            Ruins.cherryBlossom = Kord(env.BOT_TOKEN)
            Ruins.cherryBlossom.login {
                Ruins.cherryBlossom.apply {
                    on<MessageCreateEvent> {
                        when(message.content){
                            "벚꽃아 Ruins" -> {
                                message.channel.createEmbed {
                                    title = "Ruins를 플레이 하는 사람은요..\n\n\n"
                                    color = Color(65,105,225)
                                    description = "```glsl\n현재 ${Ruins.players.size} 명이 Ruins를 플레이 하고 있서요!\n\n< 유저목록 >\n${
                                        Ruins.players.joinToString(limit = 10) {
                                            "- #"+it.name+"\n"
                                        }
                                    }```"

                                }
                            }
                        }
                    }
                }


                playing("${Ruins.instance.server.onlinePlayers.size}명이 Ruins를 플레이")
            }

        }.start()
    }

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