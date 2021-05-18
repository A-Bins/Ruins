package com.bins.ruins.structure.enums.types

import net.md_5.bungee.api.ChatColor

enum class ItemType(val korean: String, val regular: String) {
    RESOURCE("자원", "     §7자원"),
    COMPONENT("잡동사니","     ${ChatColor.of("#ac6903")}잡동사니")
}