package com.bins.ruins.call.commands

import com.bins.ruins.Ruins
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*


@Suppress("DEPRECATION")
class test : CommandExecutor{
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            throw NullPointerException("잉예!!")
        }
        return false
    }
}