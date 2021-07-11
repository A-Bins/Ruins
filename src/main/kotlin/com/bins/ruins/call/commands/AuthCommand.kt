package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.classes.Auth
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AuthCommand: CommandExecutor {
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()) {
            if(p is Player){
                Auth.post(p, args[0])
            }
        }
        return false
    }

}