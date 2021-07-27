package com.bins.ruins.resistance.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.resistance.structure.enums.Recoil
import com.bins.ruins.structure.objects.utilities.Receiver.asCraft
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import net.minecraft.network.protocol.game.PacketPlayOutPosition
import org.bukkit.*
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.math.*

class Bullet(
    private val p: Player,
    private val gun: Guns,
    private val loc: Location,
    private val vertical: Float,
    private val horizontal: Float,
    private val currentMagazine: ItemStack
) {
    private val magazine = currentMagazine.itemMeta!!.persistentDataContainer.get(Resistance.magazine, PersistentDataType.INTEGER)!!.toDouble()
    private val teleportFlags: Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> = HashSet(listOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.a, PacketPlayOutPosition.EnumPlayerTeleportFlags.b, PacketPlayOutPosition.EnumPlayerTeleportFlags.c, PacketPlayOutPosition.EnumPlayerTeleportFlags.d, PacketPlayOutPosition.EnumPlayerTeleportFlags.e))


    private fun recoilPatternS(){
        val x = (  sin(( horizontal *  10 ) * (magazine / gun.magazine)) + tan(0.05) ) * 2
        val packet = PacketPlayOutPosition(0.0, 0.0, 0.0, x.toFloat(), -vertical, teleportFlags, 0, true)
        p.asCraft.handle.b.sendPacket(packet)
    }

    private fun recoilPatternCircle(){
        val x = sin(7 *(magazine / gun.magazine)).toFloat()
        val y = cos(7 *(magazine / gun.magazine)).toFloat()
        val packet = PacketPlayOutPosition(0.0, 0.0, 0.0, -x, -y, teleportFlags, 0, true)
        p.asCraft.handle.b.sendPacket(packet)
    }
    private fun defaultRecoil(){
        val packet = PacketPlayOutPosition(0.0, 0.0, 0.0, 0F, -vertical, teleportFlags, 0, true)
        p.asCraft.handle.b.sendPacket(packet)
    }
    private fun decrease(): Boolean {
        if(magazine == 0.0) return false
        currentMagazine.itemMeta = currentMagazine.itemMeta.apply {
            persistentDataContainer.set(Resistance.magazine, PersistentDataType.INTEGER, magazine.toInt()-1)
        }
        return true
    }
    private fun speed(speed: Int, locs: Location, bullet: (Location) -> Unit){
        var count = gun.maxRange / speed
        var target = locs.clone().add(locs.direction.multiply(0.2))
        val hits = arrayListOf<Player>()
        object : BukkitRunnable() {
            override fun run() {
                if(count == 0) cancel()
                val ray = target.world!!.rayTrace(target, target.direction, speed.toDouble(), FluidCollisionMode.NEVER, false, 0.1) {
                    it.type == EntityType.PLAYER && it.name != p.name
                }

                if(ray != null) {
                    if ((ray.hitBlock != null) or (ray.hitEntity != null)) {
                        if (ray.hitEntity != null && (ray.hitEntity as Player).gameMode != GameMode.CREATIVE && !hits.contains(ray.hitEntity)) {

                            val hit = ray.hitEntity as Player
                            hits.add(hit)
                            hit.noDamageTicks = 0
                            hit.maximumNoDamageTicks = 0
                            hit.damage(gun.damage)
                            hit.maximumNoDamageTicks = 20
                        }
                        if (ray.hitBlock != null) {
                            for (i in 1..10) {
                                target = target.add(target.direction.multiply(ray.hitBlock!!.location.distance(target) / 10))
                                bullet(target)
                            }
                            cancel()
                            return
                        }
                    }
                }
                for(i in 1..10){
                    val value = speed.toDouble()
                    target = target.add(target.direction.multiply(value/ 10))
                    bullet(target)
                }
                --count
            }
        }.runTaskTimer(Ruins.instance,0, 1)
    }
    private fun goBullet() {
        fun rand(from: Float, to: Float) : Float = floor((( Random().nextFloat() * (to - from) ) + from) * 10) / 10


        val accuracy = gun.hipAccuracy /* 이건 일단 나중에 조준 생기면 조준/비조준 상태 확인해서 써야함. */
        val abs = ((15 * (1 - ((accuracy) / 100))).roundToInt().toDouble() / 2)
        val locs = loc.clone().apply {
            pitch -= rand(-abs.toFloat(), abs.toFloat())
            yaw -= rand(-abs.toFloat(), abs.toFloat())
        }
        speed(gun.shootSpeed, locs) {
            it.world!!.spawnParticle(Particle.REDSTONE, it, 1, Particle.DustOptions(gun.color, 1F))
        }
    }

    fun shoot() : Boolean {
        decrease().also {
            if (!it) return false
        }
        if (gun.recoilPattern == Recoil.S)
            recoilPatternS()
        else if(gun.recoilPattern == Recoil.CIRCLE)
            recoilPatternCircle()
        else defaultRecoil()
        goBullet()

        return true
    }
}