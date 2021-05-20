package com.bins.ruins.run

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
@Suppress("DEPRECATION")
class View(private val p: Player){
    companion object{
        val cancels: ArrayList<String> = ArrayList()
        val views: ArrayList<String> = ArrayList()
        private fun create(lines: Int, title: String): Inventory{
            if(!views.contains(title))
                views.add(title)
            return Bukkit.createInventory(null, lines, Component.text(title))
        }
    }
    private val uuid = p.uniqueId
    fun inMenu() : InventoryView{
        return p.openInventory(asMenu)!!
    }
    fun inContainer() : InventoryView{
        return p.openInventory(asContainer)!!
    }
    fun inVending() : InventoryView{
        return p.openInventory(asVend)!!
    }
    fun inCraft() : InventoryView{
        return p.openInventory(asCraft)!!
    }
    fun inEngineer() : InventoryView{
        return p.openInventory(asEngineer)!!
    }
    fun inUnknown() : InventoryView{
        return p.openInventory(asUnknown)!!
    }
    fun inNurse() : InventoryView{
        return p.openInventory(asNurse)!!
    }
    fun inSoldier() : InventoryView{
        return p.openInventory(asSoldier)!!
    }
    fun inFortune() : InventoryView{
        return p.openInventory(asFortune)!!
    }
    fun inHunter() : InventoryView{
        return p.openInventory(asHunter)!!
    }
    fun inPilot() : InventoryView{
        return p.openInventory(asPilot)!!
    }
    fun inBandit() : InventoryView{
        return p.openInventory(asBandit)!!
    }
    val asContainer = create(9, "컨테이너").also { Inv ->
        val ib = ItemStack(Material.IRON_BARS).apply {
            val meta = itemMeta
            meta.setDisplayName("§f")
            itemMeta = meta
        }
        Inv.setItem(0, ib)
        Inv.setItem(1, ib)
        Inv.setItem(2, ib)
        Inv.setItem(3, ib)
        Inv.setItem(4, Vars.container[p.uniqueId])
        Inv.setItem(5, ib)
        Inv.setItem(6, ib)
        Inv.setItem(7, ib)
        Inv.setItem(8, ib)
    }
    val asMenu = create(27, "메뉴").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.PLAYER_HEAD).apply {
                val meta = itemMeta as SkullMeta
                meta.setDisplayName("§a내 정보")
                meta.lore = listOf(
                    "§7자신의 정보를 확인 가능하다",
                    "",
                    "§e확인하려면 클릭하세요!"
                )
                meta.owningPlayer = p
                itemMeta = meta
            })
        Inv.setItem(10, ItemStack(Material.SUNFLOWER).apply {
            val meta = itemMeta
            meta.setDisplayName("§a커뮤니티 주소")
            meta.lore = listOf(
                "§7디스코드, 카카오톡 오픈 채팅방, 한마포 등",
                "§7서버 추천 주소를 포함하여 링크를 볼 수 있다",
                "",
                "§e확인하려면 클릭하세요!"
            )
            itemMeta = meta
        })
        Inv.setItem(12, ItemStack(Material.CRAFTING_TABLE).apply {
            val meta = itemMeta
            meta.setDisplayName("§a조합")
            meta.lore = listOf(
                "§7간단한 물품을 제작 할 수 있다",
                "§7다른 조합 아이템은 작업대에서 조합 가능하다",
                "",
                "§e조합하려면 클릭하세요!"
            )
            itemMeta = meta
        })
        Inv.setItem(13, ItemStack(Material.COMPASS).apply {
            val meta = itemMeta
            meta.setDisplayName("§a파밍지 위치 추적")
            meta.lore = listOf(
                "§7파밍지 구역/자신 연구소 입구 위치를 추적할 수 있다.",
                "§7다른 조합 아이템은 작업대에서 조합 가능하다",
                "",
                "§e클릭하여 확인합니다!"
            )
            itemMeta = meta
        })
        Inv.setItem(14, ItemStack(Material.END_PORTAL_FRAME).apply {
            val meta = itemMeta
            meta.setDisplayName("§a컨테이너")
            meta.lore = listOf(
                "§7개인 물품을 저장할 수 있는 창고이다",
                "",
                "§7방사능 구역에 거주하는 연구팀의 퀘스트를 통해",
                "§7컨테이너의 크기를 늘릴 수 있다",
                "§c총기류는 저장이 불가능하다",
                "",
                "§e컨테이너를 열려면 클릭하세요!"
            )
            itemMeta = meta
        })
        Inv.setItem(16, ItemStack(Material.KNOWLEDGE_BOOK).apply {
            val meta = itemMeta
            meta.setDisplayName("§a튜토리얼")
            meta.lore = listOf(
                "§7서버를 진행하면서 도움이 되는 정보를 확인 가능하다",
                "",
                "§e확인하려면 클릭하세요!"
            )
            itemMeta = meta
        })
        Inv.setItem(22, ItemStack(Material.BARRIER).apply {
            val meta = itemMeta
            meta.setDisplayName("§c임무 포기")
            meta.lore = listOf(
                "§7모든 물품을 포기하고 다시 시작합니다",
                "",
                "§e더블 클릭하여 돌아갑니다!"
            )
            itemMeta = meta
        })
    }
    val asVend = create(27, "자판기").also{ Inv ->

    }
    val asCraft = create(27, "작업대").also{ Inv ->

    }
    val asEngineer = create(9, "주민: 기계공").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.SHEARS).apply {
            val meta = itemMeta
            meta.setDisplayName("§6부착물 구매")
            meta.lore = listOf(
                "§7총기 부착물을 구매할 수 있습니다",
                "",
                "§e클릭하여 확인합니다!"
            )
            itemMeta = meta
        })
    }
    val asUnknown = create(9, "주민: 이름 없는 상인").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.CHEST).apply {
            val meta = itemMeta
            meta.setDisplayName("§6물물 교환")
            meta.lore = listOf(
                "§7이름 없는 상인과 물물 교환이 가능합니다",
                "",
                "§e클릭하여 물물교환하세요!"
            )
            itemMeta = meta
        })

    }
    val asNurse = create(9, "주민: 치료사").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.REDSTONE).apply {
            val meta = itemMeta
            meta.setDisplayName("§6치료")
            meta.lore = listOf(
                "§7무료로 치료해드립니다",
                "",
                "§e치료를 받으려면 클릭하세요!"
            )
            itemMeta = meta
        })
    }
    val asSoldier = create(9, "주민: 용병").also{ Inv ->
    }
    val asFortune = create(9, "주민: 점술가").also{ Inv ->

    }
    val asHunter = create(9, "주민: 사냥꾼").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.RED_BED).apply {
            val meta = itemMeta
            meta.setDisplayName("§6침낭/침대 이동")
            meta.lore = listOf(
                "§7습격 중 빠르게 수비하러 이동할 수 있습니다",
            )
            itemMeta = meta
        })
    }
    val asBandit = create(9, "밴딧캠프 상인").also{ Inv ->
    }
    val asPilot = create(9, "주민: 헬기 조종사").also{ Inv ->
        Inv.setItem(4, ItemStack(Material.DARK_OAK_DOOR).apply {
            val meta = itemMeta
            meta.setDisplayName("§6제 1 파밍지 무작위 시작")
            meta.lore = listOf(
                "§7제 1 파밍지 중 무작위 장소로 데려다줍니다",
            )
            itemMeta = meta
        })
        Inv.setItem(4, ItemStack(Material.RED_TERRACOTTA).apply {
            val meta = itemMeta
            meta.setDisplayName("§a설산 기지 진입")
            meta.lore = listOf(
                "§7설산 기지로 진입합니다",
                "",
                "§e클릭하여 진입하세요!"
            )
            itemMeta = meta
        })
    }


}