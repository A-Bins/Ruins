package com.bins.ruins.structure.objects.utilities

import com.bins.ruins.resistance.structure.enums.Guns
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object Receiver {

    fun Any.bb(){
        Bukkit.broadcastMessage("$this")
    }
    fun String.deserializeItemStack(): ItemStack {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
        return BukkitObjectInputStream(inputStream).apply { close() }.readObject() as ItemStack
    }
    fun ItemStack.serializeItemStack(): String {
        val outputStream = ByteArrayOutputStream()
        BukkitObjectOutputStream(outputStream).also {
            it.writeObject(this)
            it.close()
        }
        return Base64Coder.encodeLines(outputStream.toByteArray())
    }
    inline fun <reified T> Any.tryCast(block: T.() -> Unit) {
        if (this is T) {
            block()
        }
    }
    inline fun <reified T> Any.typeCheck() : Boolean = this is T
}