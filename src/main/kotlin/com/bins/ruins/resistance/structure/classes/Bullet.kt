package com.bins.ruins.resistance.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.resistance.structure.enums.RecoilPattern
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.asCraft
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.packet
import net.minecraft.network.protocol.game.PacketPlayOutPosition
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.math.*

class Bullet(
    private val p: Player, // 플레이어
    private val gun: Guns, // 무슨 총인가?
    private val eyeLoc: Location, // 에임 위치
    private val vertical: Float = gun.recoilVertical, // 수직
    private val horizontal: Float = gun.recoilHorizontal , // 수평
    private val currentMagazine: ItemStack // 현재 탄량을 구하기 위한 아이템
) {
    // 현재 탄량
    private val magazine =
        currentMagazine
            .itemMeta!!
            .persistentDataContainer[Resistance.magazine, PersistentDataType.INTEGER]!!
            .toDouble()

    private fun decrease(): Boolean {
        if(magazine == 0.0) return false
        currentMagazine.itemMeta = currentMagazine.itemMeta.apply {
            persistentDataContainer.set(Resistance.magazine, PersistentDataType.INTEGER, magazine.toInt()-1)
        }
        return true
    }
    private fun bullet(
        // 총알의 슾이드
        speed: Int,
        locs: Location,
        counted: Int = gun.maxRange / speed,
        targeted: Location = locs.clone().add(locs.direction.multiply(0.2)),
        hited: ArrayList<Player> = arrayListOf(),
        bullet: (Location) -> Unit) {

        var count = counted
        var target = targeted
        val hits = hited

        /***
         *  이 친구는 이제 레이 트레이스와 파티클의 본문.
         */
        run {


            if(count == 0) return
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

        /* 재귀함수랄까? */
        1L.rl {
            bullet(speed, locs, count, target, hits, bullet)
        }
    }


    private fun goBullet() {
        // 정확도 알고리즘 ( 정해진 원 모양 에서 랜덤으로 총알이 튀기게끔. )
        fun rand(from: Float, to: Float) : Float = floor((( Random().nextFloat() * (to - from) ) + from) * 10) / 10


        val accuracy = gun.hipAccuracy /* 이건 일단 나중에 조준 생기면 조준/비조준 상태 확인해서 써야함. */
        // 기억안남 start

        val abs = ((15 * (1 - ((accuracy) / 100))).roundToInt().toDouble() / 2)
        val locs = eyeLoc.clone().apply {
            pitch -= rand(-abs.toFloat(), abs.toFloat())
            yaw -= rand(-abs.toFloat(), abs.toFloat())
        }

        // 기억 안남 end

        // 이 친구가 파티클 & 레이트레이스
        bullet(gun.shootSpeed, locs) {
            it.world!!.spawnParticle(Particle.REDSTONE, it, 1, Particle.DustOptions(gun.color, 1F))
        }
    }

    fun shoot() : Boolean {
        // 총알 감소
        decrease().also {
            if (!it) return false
        }

        // 반동
        Recoil(p, gun.recoilPattern, gun, magazine).recoil()

        // 총알 발사
        goBullet()

        return true
    }
}