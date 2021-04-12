package com.bins.ruins.call.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class test : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            if(args.isNotEmpty()){
                val p = sender as Player
                p.sendActionBar("§f3(§e+4§f)")
            }
        }
        return false
    }
}