package com.bins.ruins.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rtAsync
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import net.minecraft.network.chat.ChatComponentText
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving
import net.minecraft.world.entity.decoration.EntityArmorStand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
    companion object {
        val arHash: HashMap<UUID, LinkedList<EntityArmorStand>> = HashMap()
        val craftUnlock = NamespacedKey(Ruins.instance, "craft")
        val kitchenUnlock = NamespacedKey(Ruins.instance, "kitchen")
        val chemicalUnlock = NamespacedKey(Ruins.instance, "chemical")
        val researchUnlock = NamespacedKey(Ruins.instance, "research")
        val restUnlock = NamespacedKey(Ruins.instance, "rest")
        val medUnlock = NamespacedKey(Ruins.instance, "med")
        val weaponUnlock = NamespacedKey(Ruins.instance, "weapon")
        val bitcoinUnlock = NamespacedKey(Ruins.instance, "bitcoin")
        val recyclerUnlock = NamespacedKey(Ruins.instance, "recycler")
        val unlockList: List<NamespacedKey> = listOf(craftUnlock, kitchenUnlock, chemicalUnlock, researchUnlock,
            researchUnlock, medUnlock, weaponUnlock, bitcoinUnlock, recyclerUnlock)
    }
    private val w = Bukkit.getWorld("world")
    val profiles: ArrayList<Profile>
        get() = arrayListOf(
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
                Location(w, 9.5, 21.5, 8.5),
            ),
            Profile(
                kitchenUnlock,
                listOf(
                    "§c§l부엌 조리실",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 화로를 클릭하세요"
                ),
                listOf(
                    "§c§l부엌 조리실",
                    "§7식료품을 모아 요리를 할 수 있다",
                    "",
                    "§e사용을 위해 화로를 클릭하세요"
                ),
                Location(w, 17.5, 21.5, 3.5)
            ),
            Profile(
                chemicalUnlock,
                listOf(
                    "§c§l화학 제조 시설",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 양조기를 클릭하세요"
                ),
                listOf(
                    "§c§l화학 제조 시설",
                    "§7재료를 모아 일부 주사기, 의료품을 만들 수 있다",
                    "",
                    "§e사용을 위해 양조기를 클릭하세요"
                ),
                Location(w, 4.5, 21.5, 17.5)
            ),
            Profile(
                researchUnlock,
                listOf(
                    "§c§l연구대",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 독서대를 클릭하세요"
                ),
                listOf(
                    "§c§l연구대",
                    "§7무기나 도구의 청사진을 연구할 수 있다",
                    "",
                    "§e사용을 위해 독서대를 클릭하세요"
                ),
                Location(w, 16.5, 21.5, -4.5)
            ),
            Profile(
                restUnlock,
                listOf(
                    "§c§l휴식 공간",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 흰 침대를 클릭하세요"
                ),
                listOf(
                    "§c§l휴식 공간",
                    "§7시간을 들여 자신의 체력을 회복할 수 있다",
                    "",
                    "§e사용을 위해 흰 침대를 클릭하세요"
                ),
                Location(w, 3.5, 21.5, -6.5)
            ),
            Profile(
                medUnlock,
                listOf(
                    "§c§l구호품 제작 시설",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 작업대를 클릭하세요"
                ),
                listOf(
                    "§c§l구호품 제작 시설",
                    "§7재료를 모아 약품을 만들 수 있다",
                    "",
                    "§e사용을 위해 작업대를 클릭하세요"
                ),
                Location(w, 8.5, 21.5, -8.5)
            ),
            Profile(
                weaponUnlock,
                listOf(
                    "§c§l무기 정비 시설",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 대장장이 작업대를 클릭하세요"
                ),
                listOf(
                    "§c§l무기 정비 시설",
                    "§7무기의 탄약을 빼거나, 부착물을 붙일 수 있다",
                    "",
                    "§e사용을 위해 대장장이 작업대를 클릭하세요"
                ),
                Location(w, 11.5, 21.5, 18.5)
            ),
            Profile(
                bitcoinUnlock,
                listOf(
                    "§c§l비트코인 농장",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 작업대를 클릭하세요"
                ),
                listOf(
                    "§c§l비트코인 농장",
                    "§7재료를 모아 비트코인을 생산할 수 있다",
                    "",
                    "§e사용을 위해 작업대를 클릭하세요"
                ),
                Location(w, 10.5, 27.5, 18.5)
            ),
            Profile(
                recyclerUnlock,
                listOf(
                    "§c§l소형 분해기",
                    "§7§o해당 시설은 잠겨 있습니다",
                    "",
                    "§e잠금 해제를 위해 숫돌을 클릭하세요"
                ),
                listOf(
                    "§c§l소형 분해기",
                    "§7일부 잡동사니, 무기, 도구를 하위 재료로 분해 가능하다",
                    "",
                    "§e사용을 위해 숫돌을 클릭하세요"
                ),
                Location(w, 17.5, 21.5, 12.5)
            )
        )
    fun enable() {
        20L.rtAsync{
            val container = WorldGuard.getInstance().platform.regionContainer
            Ruins.players.filter { p ->
                val regionManager = container.get(BukkitAdapter.adapt(p.world))!!
                val loc = BlockVector3.at(p.location.x, p.location.y, p.location.z)
                val regionSet = regionManager.getApplicableRegions(loc)

                return@filter regionSet.toList().stream().anyMatch { it.id.contains("hideout") }
            }.forEach Foo@ { p ->

                val arList: LinkedList<EntityArmorStand> = LinkedList()
                profiles.filter { p.persistentDataContainer.has(it.key, PersistentDataType.STRING) }.forEach {
                    val isAfter = p.persistentDataContainer[it.key, PersistentDataType.STRING] == "true"
                    val list = if(isAfter) it.after else it.before
                    fun made() {
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
                            (p as CraftPlayer).handle.b.sendPacket(packet).also {
                                p.handle.b.sendPacket(meta)
                            }

                        }
                    }
                    fun change() {
                        arHash[p.uniqueId]!!.forEach { ar ->
                            val meta = PacketPlayOutEntityMetadata(ar.id, ar.dataWatcher, true)
                            (p as CraftPlayer).handle.b.sendPacket(meta)
                        }
                    }
                    if(arHash[p.uniqueId] != null) {
                        change()
                        return@Foo
                    }else{
                        made()
                    }                }
                arHash[p.uniqueId] = arList

            }
        }
    }
     fun disable() {
        arHash.forEach {
            it.value.forEach { v ->
                val packet = PacketPlayOutEntityDestroy(v.id)
                (Bukkit.getPlayer(it.key) as CraftPlayer).handle.b.sendPacket(packet)
            }
        }
    }
}