package com.bins.ruins.call.commands

import com.bins.ruins.structure.Farmings
import com.bins.ruins.structure.Total
import com.bins.ruins.utilities.Util
import com.bins.ruins.utilities.Util.bb
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class test : CommandExecutor{
    fun deserializeItemStack(item: String): ItemStack? {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(item))
        try {
            val dataInput = BukkitObjectInputStream(inputStream)
            dataInput.close()
            return dataInput.readObject() as ItemStack
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }
    fun serializeItemStack(item: ItemStack): String? {
        val outputStream = ByteArrayOutputStream()
        try {
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeObject(item)
            dataOutput.close()
            return Base64Coder.encodeLines(outputStream.toByteArray())
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return null
    }
    override fun onCommand(p: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(p is Player){
            val t2 = Total.create(80, 61)
            bb(t2.toRegular)
            bb(t2.toPerRegular)
            bb(t2.ratio)
            bb(t2.sum)


            bb("\n\n\n")

            val t3 = Total.create(21, 101)
            bb(t3.toRegular)
            bb(t3.toPerRegular)
            bb(t3.ratio)
            bb(t3.sum)


            bb("\n\n\n")

            val t4 = Total.create(150, 50)
            bb(t4.toRegular)
            bb(t4.toPerRegular)
            bb(t4.ratio)
            bb(t4.sum)
        }
        return false
    }
}