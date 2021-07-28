package com.bins.ruins.resistance.structure.classes

import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.resistance.structure.enums.RecoilPattern
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.packet
import net.minecraft.network.protocol.game.PacketPlayOutPosition
import org.bukkit.entity.Player
import java.util.HashSet
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Recoil(val p: Player, val pattern: RecoilPattern, val gun: Guns, val magazine: Double) {

    // 패킷 플래그
    private val teleportFlags: Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> =
        HashSet(
            listOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.a,
                PacketPlayOutPosition.EnumPlayerTeleportFlags.b,
                PacketPlayOutPosition.EnumPlayerTeleportFlags.c,
                PacketPlayOutPosition.EnumPlayerTeleportFlags.d,
                PacketPlayOutPosition.EnumPlayerTeleportFlags.e
            )
        )


    /**
     * 반동 패턴을 만들어주는 친구들
     **/
    private fun s(){
        val x = (  sin((gun.recoilHorizontal *  10 ) * (magazine / gun.magazine)) + tan(0.05) ) * 2
        PacketPlayOutPosition(0.0, 0.0, 0.0, x.toFloat(), -gun.recoilVertical, teleportFlags, 0, true).also {
            p.packet(it)
        }
    }
    private fun circle(){
        val x = sin(7 *(magazine / gun.magazine)).toFloat()
        val y = cos(7 *(magazine / gun.magazine)).toFloat()
        PacketPlayOutPosition(0.0, 0.0, 0.0, -x, -y, teleportFlags, 0, true).also {
            p.packet(it)
        }
    }
    private fun default() = PacketPlayOutPosition(0.0, 0.0, 0.0, 0F, -gun.recoilVertical, teleportFlags, 0, true).also {
        p.packet(it)
    }


    /**
     * 퍼블릭으로 이 펑션을 부르면 when으로 자동으로 인식하여 반동 패턴 펑션을 불러줌
     **/
    fun recoil() {
        when (pattern){
            RecoilPattern.S -> s()
            RecoilPattern.CIRCLE -> circle()
            RecoilPattern.DEFAULT -> default()
        }
    }
}