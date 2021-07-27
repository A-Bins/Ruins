package com.bins.ruins.scavengers

import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.scavengers.structure.enums.HearSound
import com.bins.ruins.structure.objects.utilities.Receiver.bb
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.trait.SkinTrait
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.abs

class Scavenger(val spawn: Location) {
    val scav: NPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "스캐브")
    init {
        scav.spawn(spawn)
        scav.data()[NPC.NAMEPLATE_VISIBLE_METADATA] = false
        val trait = scav.getOrAddTrait(SkinTrait::class.java) as SkinTrait
        trait.setSkinPersistent("scav",
            "ccfsb+xTZbo29KTDn6JhP3VCfohbksD4O74TDvbbXJ4JLEgt9PIBMgHQrgIhxONOjiDG8lISx7UU1z12nvMCLWaanBiZM20UAUjOdNRRIaLwkmiM9CjwX3eNKN8GEnRITUjnx4Od9SbDYcSag5f8pi74y2OCWvS8q+DEBMewc8Ei2zK5cN7iswXRkr62VzQPcaz4SnBT3NFbpwXnNGeRFqKCjLf8dF5oLGaCLTQZbe6QzoZ/Zcn4+HLSPWlNkW/hiNjhTtZGqEWmfCQRtfMTTs0F/GyNtGNHoaFUrqfz0E3+7ysOCQcRCMzGeA5z/Uz4ezw3T8yLzfuwjPAPEPZQoDt084Xh9RPYy0q+wNZlKvq9zGezlZaIvmN1mnaTSeYOK8ZYf7Qw/spWGvKmvhNtlL2YpNnAY5Hr0QFjI/NduWccl8vnCxWRzmk0/QTvP6kR0lBpRavn/lPg+7PeYCPpLMlXbV8Ht4f8qP/YoUAog2mS3+b3csRS3z08D8gz6z0LU9Pqf9Nc5a/18iU8rqgoGzHNX1Sm8dcNBEgIneEc4ZzI82UCO5JI9FnmWHOvIdYsEKygEZPVvcIOimrLGrNZo0FAu1Sqk2NIvnkzwXAU6CxbRAtCEETO0BRHa49UatzdjWM+sG1QAhDKNXnwLELmF14+0jnEerUVNa6dJ85DMu0=",
            "ewogICJ0aW1lc3RhbXAiIDogMTYyNzEyNTM4NzU2NCwKICAicHJvZmlsZUlkIiA6ICI0ZjU2ZTg2ODk2OGU0ZWEwYmNjM2M2NzRlNzQ3ODdjOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I2M2Y0ZmI2M2UzOWViNGUzNDVlYjE3NTUzMTllN2VjYWI1MzVjZDcxNTUwNjg1ZWIyMTQzYmQ5NWI4OTRmZTQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=="
        )

    }
    private val entity = scav.entity as Player
    fun nearbyHear(sound: HearSound, hear: Location) {
//        if(sound.distance <= entity.location.distance(hear)) return
        5L.rl {
            scav.teleport(
                (scav.entity as LivingEntity)
                    .location
                    .setDirection(
                        Location(scav.entity.world, -73.5, 20.0, -167.5)
                            .clone()
                            .subtract((scav.entity as LivingEntity).eyeLocation)
                            .toVector()
                    ),
                PlayerTeleportEvent.TeleportCause.UNKNOWN
            )
        }
        40L.rl{
            guard(hear)
        }

    }
    companion object {

        fun Location.right(): Vector {
            val direction = direction.normalize()
            return Vector(-direction.z, 0.0, direction.x).normalize()
        }
        fun Location.front(): Vector {
            val direction = direction.normalize()
            return Vector(direction.x, 0.0, direction.z).normalize()
        }
        fun Location.back(): Vector {
            val direction = direction.normalize()
            return Vector(-direction.x, 0.0, -direction.z).normalize()
        }

        fun Location.left(): Vector {
            val direction = direction.normalize()
            return Vector(direction.z, 0.0, -direction.x).normalize()
        }
        fun Location.neighbors(): ArrayList<Location> {
            val neighbors = arrayListOf<Location>()
            arrayOf(front(), left(), back(), right()).forEach {
                neighbors.add(clone().add(it).toCenterLocation())
            }
            return neighbors
        }
    }


    fun h(node: Location, goal: Location): Double {
        val x = node.x - goal.x
        val z = node.z - goal.z
        return x * x + z * z
    }


    fun guard(end: Location, next: Iterable<Vector>? = null) {
        lateinit var mayNext: Iterable<Vector>
        if(next == null) {



            val close: ArrayList<Location> = arrayListOf()
            val fMaps: HashMap<Location, Double> = hashMapOf()
            val gMaps: HashMap<Location, Double> = hashMapOf()
            val queue = PriorityQueue<Location> { o1, o2 ->
                if(fMaps[o1]!! < fMaps[o2]!!) return@PriorityQueue 1
                if(fMaps[o1]!! > fMaps[o2]!!) return@PriorityQueue -1
                return@PriorityQueue 0
            }
            queue.add(entity.location.toCenterLocation())




            var i = 0
            while(!queue.isEmpty()) {
                val current = queue.poll()
                i++
                if(i >= 100) {
                    "초과".bb()
                    return
                }
                if(current.distance(end) <= 1.5) break

                gMaps[current] = 0.0
                close.add(current)


                for(it in current.neighbors()) {
                    if(it.clone().add(0.0, 1.0, 0.0).block.type == Material.AIR) {

                        val gScore = gMaps[current]!! + 1
                        val fScore = gScore + h(it, current)

                        if (close.contains(it)) {

                            if (gMaps[it] == null) {
                                gMaps[it] = gScore
                            }
                            if (fMaps[it] == null) {
                                fMaps[it] = fScore
                            }

                            if (fScore >= fMaps[it]!!) {
                                continue
                            }
                        }
                        if (!queue.contains(it) || fScore < fMaps[it]!!) {
                            gMaps[it] = gScore
                            fMaps[it] = fScore
                            if (!queue.contains(it)) {
                                it.world.spawn(it, ArmorStand::class.java) { a ->
                                    a.isMarker = true
                                    a.customName = "응애"
                                    a.isVisible = false
                                    a.isCustomNameVisible = true
                                    a.isSmall = true
                                }
                                queue.add(it)

                            }
                        }
                    }
                    else it.world.spawn(it.clone().add(0.0, 2.0, 0.0), ArmorStand::class.java) { a ->
                            a.isMarker = true
                            a.customName = "맘마"
                            a.isVisible = false
                            a.isCustomNameVisible = true
                            a.isSmall = true
                        }
                }



            }






        }else mayNext = next

//        1L.rlAsync {
//            guard(end, mayNext)
//        }


    }


}