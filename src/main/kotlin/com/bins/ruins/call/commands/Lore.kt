package com.bins.ruins.call.commands

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList


class Lore : CommandExecutor {
    @Suppress("DEPRECATION")
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        try {
            if (args.size > 1) {
                if (p is Player) {
                    if (!p.isOp) return false
                    if (p.inventory.itemInMainHand.type != Material.AIR) {
                        if (p.inventory.itemInMainHand.itemMeta != null) {
                            val item = p.inventory.itemInMainHand
                            val str = java.lang.String.join(" ", *args.copyOfRange(1, args.size))
                            val i = args[0].toInt()
                            if (item.itemMeta == null) {
                                val meta = item.itemMeta!!
                                meta.lore = ArrayList(listOf(""))
                                item.itemMeta = meta
                            }
                            val li: ArrayList<String> = ArrayList(item.itemMeta!!.lore!!)
                            for (a in 0 until i) {
                                if (item.itemMeta!!.lore!!.size - 1 < i - 1) {
                                    li.add("")
                                    item.itemMeta!!.lore = li
                                }
                            }
                            li[i - 1] = ChatColor.translateAlternateColorCodes('&', str)
                            item.itemMeta!!.lore = li
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            p.sendMessage("§c다시..입력하세욧!")
            ex.printStackTrace()
            return false
        }
        return false
    }
}