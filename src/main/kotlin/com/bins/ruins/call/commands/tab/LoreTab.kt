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
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return null }
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()
        if (!p.isOp) return null

            if (args.size == 2) {
                val i = p.inventory.itemInMainHand
                i.itemMeta?.lore?.let {
                    if (it.size >= args[0].toInt()) {
                        val lore = args[0].toInt()
                        commands.add(it[lore - 1].replace("§", "&"))
                    }
                }
                StringUtil.copyPartialMatches(args[1], commands, completions)
            }
            completions.sort()
        return completions
    }
}