package com.bins.ruins.structure.enums

import com.bins.ruins.structure.interfaces.Farmable
import com.bins.ruins.structure.enums.types.ItemType
import com.bins.ruins.utilities.Util.bb
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
@Suppress("DEPRECATION")
enum class Farmings: Farmable {

    STONE{
        override fun farming(p: Player, b: Block) {
            val isCritical = b.type == Material.MOSSY_COBBLESTONE
            val inv = p.inventory
            when(inv.firstEmpty() == -1){
                true  -> {
                    val list: ArrayList<Int> = ArrayList()
                    for(i in inv.contents){
                        if(i == null)
                            continue
                        val item = i.clone()
                        item.amount = itemStack.amount
                        if((item == itemStack)and(i.amount != 64)){
                            list.add(i.amount)
                        }
                    }


                    var bool = false
                    val items = if(isCritical) bundleItemStack else itemStack
                    var over = items.amount
                    for(i in list){
                        if((over != items.amount))
                            continue
                        if((i+items.amount) <= 64)
                            bool = true
                        else{
                            over = (i+items.amount)-64
                        }
                    }
                    when(bool){
                        true ->  {
                            p.sendTitle("", "               §${if(isCritical) "a" else "7"}+${items.amount}", 0, 40, 0)
                            p.inventory.addItem(items)
                        }
                        false -> {
                            p.sendTitle("", "               §${if(isCritical) "a" else "7"}+${(items.amount-over)}, §c-$over", 0, 40, 0)
                            val overItem = items.clone()
                            overItem.amount = items.amount-over
                            p.inventory.addItem(overItem)
                            overItem.amount = over
                            p.world.dropItemNaturally(b.location, overItem)
                        }
                    }
                }
                false ->{
                    when(isCritical){
                        true -> {
                            val i = bundleItemStack
                            inv.addItem(i)
                            p.sendTitle("", "               §a+${i.amount}", 0, 40, 0)
                        }
                        false -> {
                            inv.addItem(itemStack)
                            p.sendTitle("", "               §7+${itemStack.amount}", 0, 40, 0)
                        }

                    }
                }
            }
        }
        override val signature: String = "하위"

        override val itemStack: ItemStack
            get() = ItemStack(Material.COBBLESTONE).apply {
                val meta = itemMeta
                meta.setDisplayName("§f돌")
                meta.lore = listOf(
                    "§8§o흔히 볼 수 있는 돌이다.$label",
                    ""

                )
                meta.addItemFlags(*ItemFlag.values())
                itemMeta = meta
            }

        override val label: String
            get() = type.regular

        override val type: ItemType
            get() = ItemType.RESOURCE

        override val bundleAmount: Int
            get() = (2..5).random()


        override val bundleItemStack: ItemStack
            get() = super.bundleItemStack
    },
    WOOD{
        override fun farming(p: Player, b: Block) {

        }
        override val signature: String = "하위"

        override val itemStack: ItemStack
            get() = ItemStack(Material.OAK_PLANKS).apply {
                val meta = itemMeta
                meta.setDisplayName("§f목재")
                meta.lore = listOf(
                    "§8§o나무를 가공해 만든 재료이다.$label",
                    ""
                )
                meta.addItemFlags(*ItemFlag.values())
                itemMeta = meta
            }

        override val label: String
            get() = type.regular

        override val type: ItemType
            get() = ItemType.RESOURCE

        override val bundleAmount: Int
            get() = (2..5).random()

        override val bundleItemStack: ItemStack
            get() = super.bundleItemStack
    }
}