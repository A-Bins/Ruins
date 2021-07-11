package com.bins.ruins.cherryblossom

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import com.bins.ruins.structure.objects.vars
import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.Bukkit

//        Ruins.cherryBlossom.on<DisconnectEvent.DiscordCloseEvent> {
//            kord.rest.channel.createMessage(Snowflake("835114682871578624")){
//                content = "벚꽃봇 잔다.."
//            }
//        }
class CherryBlossom {
    companion object {
        @DelicateCoroutinesApi
        fun cherryBlossomLogoutAsync() = GlobalScope.async {
            Ruins.cherryBlossom.shutdown()
        }
        @DelicateCoroutinesApi
        fun cherryBlossomInitializedAsync() = GlobalScope.async {
            Ruins.cherryBlossom = Kord(env.BOT_TOKEN)
            Ruins.cherryBlossom.login {
                Ruins.cherryBlossom.apply {
                    on<MessageCreateEvent> {
                        when{
                            message.content == "벚꽃아 리로드" -> {
                                message.reply { content = "두근 두근 리로드 횟수는! **${vars.reload["server"]}**" }
                            }
                            message.content.contains("벚꽃아 전적 ") -> {
                                val name = message.content.split("전적 ")[1]
                                val p = Bukkit.getOfflinePlayers().find { it.name == name }
                                if(p != null){
                                    if(vars.totals[p.uniqueId] == null){
                                        message.reply { content = "머야..이상해 이사람 전적이 업서!!" }
                                        return@on
                                    }
                                    message.reply { content = "${vars.totals[p.uniqueId]!!}" }
                                }else{
                                    message.reply { content = "그런 사람은 업서여!"}
                                }
                            }
                            message.content == "벚꽃아 인증" -> {
                                val auth = Auth.request(msg = message)
                                if(auth != null) {
                                    message.reply { content = "DM을 확인해주세욘!" }
                                    val auths = auth.startAsync()
                                }else {
                                    message.reply {
                                        content = "중복 인증은 몬해욘!! 멈처!"
                                    }
                                }
                            }
                            message.content == "벚꽃아 인원" -> {
                                message.reply {
                                    embed {
                                        title = "Ruins를 플레이 하는 사람은요..\n\n\n"
                                        color = Color(255, 192, 203)
                                        description = "```glsl\n현재 ${Ruins.players.size} 명이 Ruins를 플레이 하고 있서요!\n\n< 유저목록 >\n${
                                            Ruins.players.joinToString("",limit = 10) {
                                                "- #"+it.name+"\n"
                                            }
                                        }```"
                                    }
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