package com.bins.ruins.scavengers

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.r
import com.bins.ruins.Ruins.Companion.rAsync
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.scavengers.structure.classes.Path
import com.bins.ruins.scavengers.structure.classes.Path.Companion.canStandAt
import com.bins.ruins.scavengers.structure.enums.HearSound
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.trait.SkinTrait
import net.minecraft.world.entity.player.EntityHuman
import org.bukkit.HeightMap
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.*
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.util.Vector
import java.util.*

class Scavenger(val spawn: Location) {
    val scav = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "스캐브")
    init {
        scav.spawn(spawn)
        scavengers.add(this)
        scav.config()
        scav.data()[NPC.NAMEPLATE_VISIBLE_METADATA] = false
        val trait = scav.getOrAddTrait(SkinTrait::class.java) as SkinTrait
        trait.setSkinPersistent("scav",
            "ccfsb+xTZbo29KTDn6JhP3VCfohbksD4O74TDvbbXJ4JLEgt9PIBMgHQrgIhxONOjiDG8lISx7UU1z12nvMCLWaanBiZM20UAUjOdNRRIaLwkmiM9CjwX3eNKN8GEnRITUjnx4Od9SbDYcSag5f8pi74y2OCWvS8q+DEBMewc8Ei2zK5cN7iswXRkr62VzQPcaz4SnBT3NFbpwXnNGeRFqKCjLf8dF5oLGaCLTQZbe6QzoZ/Zcn4+HLSPWlNkW/hiNjhTtZGqEWmfCQRtfMTTs0F/GyNtGNHoaFUrqfz0E3+7ysOCQcRCMzGeA5z/Uz4ezw3T8yLzfuwjPAPEPZQoDt084Xh9RPYy0q+wNZlKvq9zGezlZaIvmN1mnaTSeYOK8ZYf7Qw/spWGvKmvhNtlL2YpNnAY5Hr0QFjI/NduWccl8vnCxWRzmk0/QTvP6kR0lBpRavn/lPg+7PeYCPpLMlXbV8Ht4f8qP/YoUAog2mS3+b3csRS3z08D8gz6z0LU9Pqf9Nc5a/18iU8rqgoGzHNX1Sm8dcNBEgIneEc4ZzI82UCO5JI9FnmWHOvIdYsEKygEZPVvcIOimrLGrNZo0FAu1Sqk2NIvnkzwXAU6CxbRAtCEETO0BRHa49UatzdjWM+sG1QAhDKNXnwLELmF14+0jnEerUVNa6dJ85DMu0=",
            "ewogICJ0aW1lc3RhbXAiIDogMTYyNzEyNTM4NzU2NCwKICAicHJvZmlsZUlkIiA6ICI0ZjU2ZTg2ODk2OGU0ZWEwYmNjM2M2NzRlNzQ3ODdjOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I2M2Y0ZmI2M2UzOWViNGUzNDVlYjE3NTUzMTllN2VjYWI1MzVjZDcxNTUwNjg1ZWIyMTQzYmQ5NWI4OTRmZTQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=="
        )
    }
    companion object {
        fun faceDirection(want: Location, target: Location): Location {
            val dir = target.clone().subtract(want).toVector()
            return want.clone().setDirection(dir)
        }
        val scavengers: ArrayList<Scavenger> = arrayListOf()
        val Location.lowest: Location? get() {
            repeat(35) {
                if(clone().subtract(0.0, it.toDouble(), 0.0).canStandAt)
                    return clone().subtract(0.0, it.toDouble(), 0.0).toCenterLocation()
            }
            return null
        }
    }


    private fun NPC.config() {
        if(scav.isSpawned) {
            scav.isProtected = false
            1L.rl {
                (scav.entity as Player).getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
                (scav.entity as Player).health = 100.0
            }
        }
        else 1L.rl { config() }
    }
    private var cancel = false
    private var run = false
    private val entity = scav.entity as Player
    fun nearbyHear(sound: HearSound, hear: Location) {
        if(sound.distance <= entity.location.distance(hear)) return
        val goal = hear.lowest ?: return
        if(scav.isSpawned) {
            readyToGuard(goal.toCenterLocation())
            return
        }
        1L.rl{
            nearbyHear(sound, goal)
        }

    }
    private fun readyToGuard(end: Location, ready: Boolean = false) {
        if(run){
            if(!ready) cancel = true
        }else {
            guard(end)
            return
        }
        1L.rl {
            if(!cancel) guard(end)
            else readyToGuard(end, true)
        }
    }
    private fun guard(end: Location, list: MutableList<Location>? = null) {
        if(!scav.isSpawned) return
        if(cancel) {
            cancel = false
            run = false
            scav.navigator.cancelNavigation()
            return
        }
        if(scav.entity.location.distance(end) <= 1) {
            run = false
            scav.navigator.cancelNavigation()
            return
        }

        val mayList: MutableList<Location> = if(list == null) {
            scav.navigator.cancelNavigation()
            run = true
            rAsync {
                val path = Path(scav.entity.location.toHighestLocation().apply { y += 1 }.toCenterLocation(), end).goalToWay()
                if (path.isEmpty()) {
                    run = false
                    return@rAsync
                }
                r { guard(end, path.map { it!! }.toMutableList()) }
            }
            return
        }else list


        val go = mayList.first()
        if(go.distance(scav.entity.location.toCenterLocation()) == 0.0) {
            mayList.remove(go)
            if(mayList.isEmpty()) return
            /* fake가 다음 목적지인데 go의 방향을 fake를 보게끔해서 그쪽방향으로 올려줘야함 ㅇㅇ */
            val fake = faceDirection(go, mayList.first()).add(faceDirection(go, mayList.first()).direction).add(faceDirection(go, mayList.first()).direction).add(faceDirection(go, mayList.first()).direction)
            fake.pitch = 0F
            fake.world.spawn(fake, ArmorStand::class.java) {
                it.isSmall = true
                it.setGravity(false)
            }
            scav.navigator.setTarget(arrayListOf(fake.toVector()))
        }

        1L.rl {
            guard(end, mayList)
        }
    }



}