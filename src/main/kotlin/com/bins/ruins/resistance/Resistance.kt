package com.bins.ruins.resistance

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rt
import com.bins.ruins.resistance.events.EvtCheck
import com.bins.ruins.resistance.events.EvtReload
import com.bins.ruins.resistance.events.EvtShoot
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.objects.utilities.MetaReceiver.Companion.toCrossBowMeta
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*

@Suppress("DEPRECATION")
class Resistance(p: Player) {

    /**
     * 연사 시스템을 담당 해주는 객체 친구들
     **/
    init {
        resistances[p.uniqueId] = this
    }
    private var time = 0L /* 처음 init 되면 0L로 설정되는 친구임 */

    // 만약 처음에 초기화된 time 변수를 현재 currentTimeMillis에 감소 시켜주면 190~210에 존재하는가
    fun isAuto(): Boolean = (System.currentTimeMillis()-time in 190L..210L).also {
        time = System.currentTimeMillis() /* 여부와 관계 없이 호출시 time을 현재 currentTimeMillis로 대입. */
    }

    /**
     * 별 볼일 없는 친구들임.
     **/
    companion object {

        val ItemStack.isGun: Boolean
            get() = Guns.values().toList().stream().anyMatch {
                itemMeta?.displayName == it.displayName
            }
        val magazine = NamespacedKey(Ruins.instance, "magazine")
        val maxMagazine = NamespacedKey(Ruins.instance, "maxMagazine")
        val ammo = NamespacedKey(Ruins.instance, "ammo")
        val resistances: HashMap<UUID, Resistance> = HashMap()
        val Player.resistance: Resistance
            get() {
                if(resistances[uniqueId] == null) Resistance(this)
                return resistances[uniqueId]!!
            }
        fun entityEquipment(entityId: Int, slot: EquipmentSlot, item: ItemStack): PacketContainer {
            fun EquipmentSlot.convertToItemSlot(): EnumWrappers.ItemSlot {
                return when (this) {
                    EquipmentSlot.HAND -> EnumWrappers.ItemSlot.MAINHAND
                    EquipmentSlot.OFF_HAND -> EnumWrappers.ItemSlot.OFFHAND
                    EquipmentSlot.FEET -> EnumWrappers.ItemSlot.FEET
                    EquipmentSlot.LEGS -> EnumWrappers.ItemSlot.LEGS
                    EquipmentSlot.CHEST -> EnumWrappers.ItemSlot.CHEST
                    EquipmentSlot.HEAD -> EnumWrappers.ItemSlot.HEAD
                }
            }
            return PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT).apply {
                integers.write(0, entityId)
                slotStackPairLists.write(0, Collections.singletonList(Pair(slot.convertToItemSlot(), item)))
            }
        }
        fun scheduler(){
            1L.rt {
               Ruins.players.filter { it.inventory.itemInMainHand.isGun }.forEach first@{ p ->
                        Ruins.players.forEach { target ->
                            if(target.name == p.name) return@forEach
                            ProtocolLibrary.getProtocolManager().sendServerPacket(
                                target, entityEquipment(p.entityId,
                                    EquipmentSlot.HAND, ItemStack(Material.CROSSBOW).apply {
                                        val meta = itemMeta!!.toCrossBowMeta()
                                        meta.addChargedProjectile(ItemStack(Material.ARROW))
                                        itemMeta = meta
                                    })
                            )
                        }
                    }
                }
            }
        fun configs(): Array<Listener> {
            return arrayOf(EvtShoot(), EvtReload(), EvtCheck())
        }

    }
}