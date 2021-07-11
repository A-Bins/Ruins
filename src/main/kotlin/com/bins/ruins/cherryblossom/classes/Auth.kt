package com.bins.ruins.cherryblossom.classes

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.Channel
import dev.kord.rest.builder.interaction.ChannelBuilder
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.concurrent.thread

class Auth private constructor(private val msg: Message){
    companion object {
        fun post(p: Player,s: String): Boolean {
            if(requests.containsKey(UUID.fromString(s))){
                "헤헹 $s".bb()
                requesters[requests[UUID.fromString(s)]!!.first] = p.uniqueId
                requests[UUID.fromString(s)]!!.second.complete = true
                return true
            }
            return false
        }
        val authes: HashMap<Long, Auth> = HashMap()
        val requests: HashMap<UUID, Pair<Long, Auth>> = HashMap()
        val requesters: HashMap<Long, UUID> = HashMap()
        fun request(msg: Message): Auth? {
            if(authes.containsKey(msg.author!!.id.value)) return null
            if(requesters.containsKey(msg.author!!.id.value)) return null
            return Auth(msg)
        }
    }
    private val authCode = UUID.randomUUID()
    private var count = 60
    private val id = msg.author!!.id.value
    private var complete = false
    private lateinit var authMsg: Message
    private lateinit var authCount: Message
    private fun EmbedBuilder.config() {
        color = Color(255, 192, 203)
        title = "**인증**"
        description = "```인증 코드 : $authCode```"
        val f = EmbedBuilder.Footer()
        f.text = "Ruins 서버 내에서 \"/인증 $authCode\" 를 해주세욘!"
        footer = f
    }
    private fun EmbedBuilder.count() {

        color = Color(255, 192, 203)
        description = "```남은 시간 : $count```"
    }
    @DelicateCoroutinesApi
    fun startAsync() = GlobalScope.async {
        requests[authCode] = id to this@Auth
        authMsg = msg.author!!.getDmChannel().createEmbed { config() }
        authCount = msg.author!!.getDmChannel().createEmbed { count() }
        thread {
            while(count != 0) {
                --count
                val task = GlobalScope.async {
                    authCount.edit { embed { count() } }
                    authMsg.edit {
                        embed {
                            if (count == 0) {
                                authCount.delete()
                                color = Color(255, 192, 203)
                                description = "```시간이 초과되었습니다. 다시 시도해주세욘```"
                                requests.remove(authCode)
                            } else if (complete) {
                                authCount.delete()
                                description = "```인증 완료!```"
                                val f = EmbedBuilder.Footer()
                                color = Color(255, 192, 203)
                                f.text = "${Bukkit.getOfflinePlayer(requesters[msg.author!!.id.value]!!).name} | ${requesters[msg.author!!.id.value]}"
                                f.icon = "https://crafatar.com/avatars/${requesters[msg.author!!.id.value]!!}?size=25`"
                                footer = f
                                requests.remove(authCode)
                            }
                        }
                    }
                }
                Thread.sleep(1000)
            }
        }.start()
    }
    init {

    }
}