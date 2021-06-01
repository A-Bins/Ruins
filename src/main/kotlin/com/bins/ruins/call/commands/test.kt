package com.bins.ruins.call.commands

import com.bins.ruins.structure.classes.Stash.Companion.stash
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/*
val array: ArrayList<ItemStack> = arrayListOf()
for(i in 0..54){
    array.add(ItemStack(Material.STONE))
}
val d = Drawer(*array.toTypedArray(), unlockState = 1)
Strash(p.uniqueId, d)
*/

@Suppress("DEPRECATION")
class test : CommandExecutor{
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            p.stash!!.drawers.withIndex().forEach { s -> Bukkit.broadcastMessage("${s.index} ${s.value.items.hashCode()} ${s.hashCode()}") }
        }
        return false
    }
}