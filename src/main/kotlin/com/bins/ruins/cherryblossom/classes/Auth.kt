package com.bins.ruins.cherryblossom.classes

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.modify.embed
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class Auth private constructor(private val msg: Message){
    companion object {
        fun post(p: Player,s: String): Boolean {
            if(requests.containsKey(UUID.fromString(s))){
                completers[requests[UUID.fromString(s)]!!.first] = p.uniqueId
                requests[UUID.fromString(s)]!!.second.complete = true
                return true
            }
            return false
        }
        val authes: HashMap<Long, Auth> = HashMap()
        val requests: HashMap<UUID, Pair<Long, Auth>> = HashMap()
        val completers: HashMap<Long, UUID> = HashMap()
        fun request(msg: Message): Auth? {
            if(authes.containsKey(msg.author!!.id.value.toLong())) return null
            if(completers.containsKey(msg.author!!.id.value.toLong())) return null
            return Auth(msg)
        }
    }
    private val authCode = UUID.randomUUID()
    private var count = 60
    private val id = msg.author!!.id.value.toLong()
    private var complete = false
    private lateinit var authMsg: Message
    private lateinit var authCount: Message
    private fun EmbedBuilder.plzAuth() {
        color = Color(255, 192, 203)
        title = "**인증**"
        description = "```인증 코드 : $authCode```"
        footer = EmbedBuilder.Footer().apply {
            text = "Ruins 서버 내에서 \"/인증 $authCode\" 를 해주세욘!"
        }
    }
    private fun EmbedBuilder.count() {

        color = Color(255, 192, 203)
        description = "```남은 시간 : $count```"
    }
    @DelicateCoroutinesApi
    fun startAsync() = GlobalScope.async {
        requests[authCode] = id to this@Auth
        authMsg = msg.author!!.getDmChannel().createEmbed { plzAuth() }
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
                                color = Color(255, 192, 203)
                                footer = EmbedBuilder.Footer().apply {
                                    text = "${Bukkit.getOfflinePlayer(completers[msg.author!!.id.value.toLong()]!!).name} | ${completers[msg.author!!.id.value.toLong()]}"
                                    icon = "https://crafatar.com/avatars/${completers[msg.author!!.id.value.toLong()]!!}?size=25`"
                                }
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