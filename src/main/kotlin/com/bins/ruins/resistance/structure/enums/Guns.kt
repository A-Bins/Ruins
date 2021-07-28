package com.bins.ruins.resistance.structure.enums

import com.bins.ruins.resistance.Resistance
import com.bins.ruins.resistance.structure.classes.Bullet
import com.bins.ruins.resistance.structure.interfaces.Gunable
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import net.md_5.bungee.api.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.collections.HashMap


@Suppress("DEPRECATION")
enum class Guns: Gunable {
    WK416A5{
        override val color: Color = Color.AQUA
        override val ammo = Ammo.ABIN_5_56
        override val displayName = "§fWK416A5"
        override val rpm = 700.0
        override val sound: HashMap<Sound, HashMap<Float, Long>> = HashMap<Sound, java.util.HashMap<Float, Long>>().apply {

        }

        override val damage = 0.5
        override val recoilPattern = Recoil.S
        override val hipAccuracy = 80.0
        override val pointAccuracy = 97.0
        override val shootSpeed = 5 /* Meter/Ticks */
        override val magazine = 30
        override val recoilVertical = 1F
        override val recoilHorizontal = 1.37F
        override val maxRange = 150
        override val okRange = 100
        override val ignoreBlock = arrayOf(Material.WHITE_WOOL)
        override val mode = Mode.SEMI_AUTO
        override val description = arrayOf(
            "${ChatColor.of("#4b4b4b")}돌격 소총",
            "§6사용 탄약: §f${ammo.signature}",
            "§6장탄수: §f${magazine}발",
            "",
            "${ChatColor.of("#4992c6")}피해량      §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7${damage}",
            "${ChatColor.of("#4992c6")}연사 속도   §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7${rpm}RPM",
            "${ChatColor.of("#4992c6")}정확도      §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7${hipAccuracy}%, ${pointAccuracy}%",
            "",
            "${ChatColor.of("#4992c6")}반동 패턴: §f${recoilPattern}자",
            "",
            "${ChatColor.of("#4992c6")}   탄속     §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7m/s",
            "${ChatColor.of("#4992c6")}최대 사거리 §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7m",
            "${ChatColor.of("#4992c6")}유효 사거리 §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m §f§m  §f §7m",
            "${ChatColor.of("#4992c6")}발사 방식: §f단발/연사§8/점사",
        )
    };
    fun give(p: Player) {
        p.inventory.addItem(ItemStack(Material.STONE_PICKAXE).apply {
            itemMeta = itemMeta.apply {
                addItemFlags(*ItemFlag.values())
                setDisplayName(this@Guns.displayName)
                lore = description.toMutableList()
                persistentDataContainer.set(
                    Resistance.magazine,
                    PersistentDataType.INTEGER,
                    magazine
                )
                persistentDataContainer.set(
                    Resistance.maxMagazine,
                    PersistentDataType.INTEGER,
                    magazine
                )
                persistentDataContainer.set(
                    Resistance.ammo,
                    PersistentDataType.STRING,
                    ammo.signature

                )
            }
        })
    }
    fun shoot(p: Player, current: ItemStack) = Bullet(p, this, p.eyeLocation, recoilVertical, recoilHorizontal, current).shoot()
}