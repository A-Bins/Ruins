package com.bins.ruins.structure.classes

import com.bins.ruins.Ruins
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.persistence.PersistentDataType

class Hideout {
    class Profile(val key: NamespacedKey, val before: List<String>, val after: List<String>, val loc: Location) {
        var height = 0.0
            private set
        private val Boolean.nextTitle: String
            get() = when(this) {
                true -> after[(height / 0.25).toInt()]
                false -> before[(height / 0.25).toInt()]
            }

        fun next(after: Boolean): String = after.nextTitle.also {
            height += 0.25
        }
    }
    private val w = Bukkit.getWorld("world")
    val profiles: ArrayList<Profile> = arrayListOf(
        Profile(
            craftUnlock,
            listOf(
                "§c§l작업대",
                "§7§o해당 시설은 잠겨 있습니다",
                "",
                "§e잠금 해제를 위해 작업대를 클릭하세요"
            ),
            listOf(
                "§c§l작업대",
                "§7재료를 모아 잡동사니(도구), 무기를 만들 수 있다",
                "",
                "§e사용을 위해 작업대를 클릭하세요"
            ),
            Location(w, 9.5, 21.5, 7.5)
        )
    )
    fun enable() {
        Ruins.scheduler.runTaskTimer(Ruins.instance, Runnable {
            profiles.forEach {
                val arList: ArrayList<EntityArmorStand> = arrayListOf()
                val container = WorldGuard.getInstance().platform.regionContainer
                Ruins.players.filter { p ->
                    val regionManager = container.get(BukkitAdapter.adapt(p.world))!!
                    val loc = BlockVector3.at(p.location.x, p.location.y, p.location.z)
                    val regionSet = regionManager.getApplicableRegions(loc)

                    return@filter regionSet.toList().stream().anyMatch { it.id.contains("hideout") } && p.persistentDataContainer.has(it.key, PersistentDataType.STRING)
                }.forEach { p ->
                    val isAfter = p.persistentDataContainer[it.key, PersistentDataType.STRING] == "true"
                    val list = if(isAfter) it.after else it.before
                    repeat(list.size) { _ ->
                        val world = (it.loc.world as CraftWorld).handle
                        arList.add(EntityArmorStand(world, it.loc.x, it.loc.y - it.height, it.loc.z).apply {
                            customNameVisible = true
                            customName = ChatComponentText(it.next(isAfter))
                            isNoGravity = true
                            isInvisible = true
                            isMarker = true
                        })
                    }
                    arList.forEach { ar ->

                        val packet = PacketPlayOutSpawnEntityLiving(ar)
                        val meta = PacketPlayOutEntityMetadata(ar.id, ar.dataWatcher, true)
                        Ruins.players.forEach { p ->
                            (p as CraftPlayer).handle.playerConnection.sendPacket(packet)
                            p.handle.playerConnection.sendPacket(meta)
                        }
                    }
                }
            }
        }, 10,10)
    }
    fun disable() {

    }
    companion object {

        val craftUnlock = NamespacedKey(Ruins.instance, "craft")
    }
}