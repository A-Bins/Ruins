package com.bins.ruins.call.events.actions

import com.bins.ruins.structure.classes.View
import net.citizensnpcs.api.event.NPCRightClickEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EvtNpcRightClick : Listener {
    @EventHandler
    fun event(e: NPCRightClickEvent){
        val name = e.npc.name
        val p = e.clicker
        val view = View(p)
        when(name){
            "기계공" -> view.inEngineer()
            "이름 없는 상인" -> view.inUnknown()
            "치료사" -> view.inNurse()
            "용병" -> view.inSoldier()
            "점술사" -> view.inFortune()
            "사냥꾼" -> view.inHunter()
            "헬기 조종사" -> view.inPilot()
            "밴딧캠프 상인" -> view.inBandit()

        }

    }
}