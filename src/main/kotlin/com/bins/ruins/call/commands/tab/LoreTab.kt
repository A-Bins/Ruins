package com.bins.ruins.call.commands.tab

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.lang.Exception
import java.util.ArrayList
@Suppress("DEPRECATION")
class LoreTab : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String>? {
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()
        if (sender !is Player) return null
        if (!sender.isOp()) return null
        try {
            if (args.size == 2) {
                val i = sender.inventory.itemInMainHand
                if (i.itemMeta!!.lore != null) {
                    if (i.itemMeta!!.lore!!.size >= args[0].toInt()) {
                        val lore = args[0].toInt()
                        commands.add(i.itemMeta!!.lore!![lore - 1].replace("§", "&"))
                    }
                }
                StringUtil.copyPartialMatches(args[1], commands, completions)
            }
            completions.sort()
        } catch (ex: Exception) {
            return null
        }
        return completions
    }
}