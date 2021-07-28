package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.CherryBlossom
import com.bins.ruins.cherryblossom.classes.Auth
import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AuthCommand: CommandExecutor {
    @DelicateCoroutinesApi
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return false }
        if(args.isNotEmpty()) {
            if (Auth.post(p, args[0])) {
                val async = GlobalScope.async {
                    CherryBlossom.minecrafts().forEach {
                        Ruins.cherryBlossom.rest.channel.createMessage(it.id) {
                            embed {
                                color = Color(50, 50, 50)
                                author = EmbedBuilder.Author().apply {
                                    icon = "https://crafatar.com/avatars/${p.uniqueId}?size=50"
                                    name = "${p.name}님이 인증을 완료 하셨습니다!"
                                }
                            }
                        }
                    }
                }
            }

        }
        return false
    }

}