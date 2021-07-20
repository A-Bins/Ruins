package com.bins.ruins.structure.objects.utilities

import com.bins.ruins.Ruins
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.Receiver.packet
import com.bins.ruins.structure.objects.utilities.Receiver.player
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
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
import kotlin.reflect.KProperty

@Suppress("DEPRECATION")
object Receiver {
    fun Player.packet(packet: PacketContainer) = ProtocolLibrary.getProtocolManager().sendServerPacket(this, packet)
    fun PacketContainer.everyone() = Ruins.players.forEach { ProtocolLibrary.getProtocolManager().sendServerPacket(it, this) }
    val String.player: Player?
        get() = Bukkit.getPlayer(this)
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