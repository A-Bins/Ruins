package com.bins.ruins.structure.enums.items.medicals

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.bins.ruins.resistance.structure.enums.Guns
import com.bins.ruins.structure.enums.types.SubTag
import com.bins.ruins.structure.enums.types.Tag
import com.bins.ruins.structure.interfaces.items.Medical
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.math.BigDecimal

enum class Syringe: Medical {

    LEMON {
        override val Dname = "§f레몬 주사기"
        override val description: ArrayList<String> = arrayListOf(
            "§8#의료품 #주사기",
            "",
            "§6사용 시간: §f5s",
            "§6효과: §f10초간 이동 속도 상승",
            "§6패널티: §f효과가 끝난 후 멀미 10초 발생"
        )

        override val item = ItemStack(Material.IRON_HORSE_ARMOR).apply {
            val meta = itemMeta
            meta!!.setDisplayName(Dname)
            meta.lore = description
            meta.addItemFlags(*ItemFlag.values())
            itemMeta = meta
        }
        override val subTag = SubTag.SYRINGE
        override val time = 5.0
        override fun effect(p: Player) {
            used(time.toBigDecimal(), p) {
                p.addPotionEffect(PotionEffect(PotionEffectType.SPEED,20*10, 0, false, false, false))
                (20*10L).rl {
                    p.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION,20*10, 0, false, false, false))
                }
            }
        }

    };
    protected fun used(wait: BigDecimal, p: Player, want: () -> Any){
        if(wait.toDouble() == 0.0) want().also {
            1L.rl {
                p.sendRawMessage("§7사용까지 §f0.00s")
            }
        }
        else 1L.rl {
            p.sendRawMessage("§7사용까지 §f${wait}s")
            used("$wait".toBigDecimal() - 0.05.toBigDecimal(), p, want)
        }



    }
    companion object {
        fun catch(find: String): Syringe {
            try {
                return values().filter { find == it.Dname }[0]
            }catch(i: IndexOutOfBoundsException){
                throw IllegalArgumentException("${Syringe::class.java} 에서 catch() 메서드의 인자인 find : $find 인수가 알맞지 않는 값이에요!")
            }
        }
        val ItemStack.isSyringe: Boolean
            get() = values().toList().stream().anyMatch { itemMeta?.displayName == it.item.itemMeta!!.displayName }
    }

}
