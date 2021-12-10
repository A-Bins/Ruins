package com.bins.ruins.cherryblossom

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.cherryBlossom
import com.bins.ruins.Ruins.Companion.players
import com.bins.ruins.Ruins.Companion.r
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.structure.objects.env
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import com.bins.ruins.structure.objects.vars
import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import kotlin.math.round

class CherryBlossom {
    companion object {
        suspend fun minecrafts(): ArrayList<GuildChannel> = arrayListOf<GuildChannel>().apply {
            flow { cherryBlossom.guilds.collect { emit(it) } }.collect {
                flow { it.channels.collect { emit(it) } }.collect {
                    if (it.asChannel().data.topic.value?.contains("#Minecraft") == true)
                        this@apply.add(it)
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        suspend fun cherryBlossomLogout() {
            println("가낟라마바사아자차팤타ㅠㅏ")
            cherryBlossom.shutdown()
        }

        @OptIn(DelicateCoroutinesApi::class)
        suspend fun cherryBlossomInit() {
            cherryBlossom = Kord(env.BOT_TOKEN)
            cherryBlossom.login {
                r {
                    players.filter { it.isOp }.forEach { it.sendMessage("${ChatColor.of("#f2687f")}벚꽃봇 §r동작") }
                }
                presence { playing("${Ruins.instance.server.onlinePlayers.size}명이 Ruins를 플레이") }
                cherryBlossom.on<MessageCreateEvent> {
                    if (message.author?.id == cherryBlossom.selfId) return@on
                    if (message.channel.asChannel().data.topic.value?.contains("#Minecraft") == true) {
                        val m = message.getGuild().getMember(message.author!!.id)
                        "<${if (m.nickname == null) "${m.displayName}#${m.data.discriminator}" else m.nickname}> ${message.content}".bb()
                    }
                    if (message.channel.asChannel().data.topic.value?.contains("#Command") != true) return@on
                    when {
                        message.content == "벚꽃아 리로드" -> {
                            message.reply {
                                embed {
                                    color = Color(47, 49, 54)
                                    val total = (vars.reload["server"]!! * 5) + (vars.reload["server"]!! * 4)
                                    description = "리로드 횟수는! ${vars.reload["server"]} 이빈다!! \n" +
                                            "컴파일||빌드|| 하는데에만 ${vars.reload["server"]!! * 5}초가 걸려쓰빈다!\n" +
                                            "또 리로드하는데에만 ${vars.reload["server"]!! * 4}초가 걸려쓰빈다! \n" +
                                            "그래서 총 ${total.toDouble()} 초, ${
                                                round((total / 60.0) * 100) / 100.0
                                            } 분, ${
                                                round((total / 60.0 / 60.0) * 100) / 100.0
                                            } 시간을 썻스빈다!!"
                                }
                            }
                        }
                        message.content.length >= 7 -> {
                            if (message.content.substring(0, 7) == "벚꽃아 전적 ") {
                                val name = message.content.split("전적 ")[1]
                                val p = Bukkit.getOfflinePlayers().find { it.name == name }
                                if (p != null) {
                                    if (vars.totals[p.uniqueId] == null) {
                                        message.reply { content = "머야..이상해 이사람 전적이 업서!!" }
                                        return@on
                                    }
                                    message.reply { content = "${vars.totals[p.uniqueId]!!}" }
                                } else message.reply { content = "그런 사람은 업서여!" }
                            }
                        }
                        message.content == "벚꽃아 인증" -> {
                            val auth = Auth.request(msg = message)
                            if (auth != null) {
                                message.reply { content = "DM을 확인해주세욘!" }
                                val auths = auth.startAsync()
                            } else message.reply { content = "엄..못하세욘.." }
                        }
                        message.content == "벚꽃아 인원" -> {
                            message.reply {
                                embed {
                                    title = "Ruins를 플레이 하는 사람은요..\n\n\n"
                                    color = Color(255, 192, 203)
                                    description =
                                        "```glsl\n현재 ${Ruins.players.size} 명이 Ruins를 플레이 하고 있서요!\n\n< 유저목록 >\n${
                                            Ruins.players.joinToString("", limit = 10) {
                                                "- #" + it.name + "\n"
                                            }
                                        }```"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}