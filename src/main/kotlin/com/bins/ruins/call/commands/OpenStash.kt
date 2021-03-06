package com.bins.ruins.call.commands

import com.bins.ruins.structure.classes.Stash.Companion.inStash
import com.bins.ruins.structure.classes.Stash.Companion.stash
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OpenStash : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return false }
        p.inStash()
        return false
    }
}