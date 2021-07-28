package com.bins.ruins.call.commands.tab

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.lang.Exception
import java.util.ArrayList

@Suppress("DEPRECATION")
class NameTab : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return null }
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()
        if (!p.isOp) return null

        if (args.size == 1) {
            val i = p.inventory.itemInMainHand
            i.itemMeta?.let {
                commands.add(it.displayName.replace("§", "&"))
                StringUtil.copyPartialMatches(args[0], commands, completions)
            }
        }
        completions.sort()
        return completions
    }
}