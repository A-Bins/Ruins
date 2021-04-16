package com.bins.ruins.custom

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockBreakEvent

class StoneFileBreakEvent(
    val player: Player,
    val block: Block,
    private val event: BlockBreakEvent
    ) : Event(), Cancellable {

    private var cancelled = false

    override fun setCancelled(cancel: Boolean) {
        event.isCancelled = cancel
        cancelled = cancel
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun getHandlers(): HandlerList {
        return handler
    }

    companion object {
        @JvmStatic
        private val handler = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return this.handler
        }
    }
}