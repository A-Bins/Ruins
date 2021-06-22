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
    val Player.targetedItemEntity: Item?
        get() {
            for(i in 0..35) {
                val loc : Location = this.eyeLocation.add(this.location.direction.multiply(i.toDouble()/10))
                val list = loc.world.getNearbyEntitiesByType(Item::class.java, loc, 0.15, 0.15, 0.15)
                for (e in list) {
                    return e
                }
            }
            return null
        }

    fun Any.bb(){
        Bukkit.broadcastMessage("$this")
    }
    fun String.deserializeItemStack(): ItemStack {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(this))
        val dataInput = BukkitObjectInputStream(inputStream)
        dataInput.close()
        return dataInput.readObject() as ItemStack
    }
    fun ItemStack.serializeItemStack(): String {
        val outputStream = ByteArrayOutputStream()
        val dataOutput = BukkitObjectOutputStream(outputStream)
        dataOutput.writeObject(this)
        dataOutput.close()
        return Base64Coder.encodeLines(outputStream.toByteArray())
    }
    inline fun <reified T> Any.tryCast(block: T.() -> Unit) {
        if (this is T) {
            block()
        }
    }
    inline fun <reified T> Any.typeCheck() : Boolean = this is T
}