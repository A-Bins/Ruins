package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Spawn: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return false }
        p.teleport(Location(Bukkit.getWorld("world"), 5.5, 20.0, 4.5))
        return false
    }
}