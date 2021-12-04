package com.bins.ruins.call.commands

import com.bins.ruins.resistance.structure.enums.Ammo
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveGun: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = sender as? Player ?: run { sender.sendMessage("아잇 머해!"); return false }
        Guns.WK416A5.give(p)
        p.inventory.addItem(Ammo.ABIN_5_56.ammo().apply {
            amount = 64
        })
        return false
    }
}