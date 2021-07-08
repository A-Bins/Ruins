package com.bins.ruins.structure.classes

import com.bins.ruins.Ruins
import com.bins.ruins.Ruins.Companion.rl
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.stream.JsonWriter
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.entity.Player
import org.json.simple.JSONObject
import java.io.StringWriter
import java.io.Writer


class Toast(val key: NamespacedKey, val title: String, val description: String, val frame: String, val icon: String) {
    init {
        Bukkit.getUnsafe().loadAdvancement(key, json)
    }
    val json: String
        get() {
            val icons = JsonObject().apply {
                addProperty("item", icon)
            }
            val display = JsonObject().apply {
                add("icon", icons as JsonElement)
                addProperty("title", title);
                addProperty("description", description);
                addProperty("background", "minecraft:textures/gui/advancements/backgrounds/adventure.png");
                addProperty("frame", frame);
                addProperty("announce_to_chat", false);
                addProperty("show_toast", true);
                addProperty("hidden", true);
            }
            val trigger = JsonObject().apply {

                addProperty("trigger", "minecraft:impossible");
            }
            val criteria = JsonObject().apply {

                add("impossible", trigger as JsonElement)
            }
            return GsonBuilder().setPrettyPrinting().create().toJson(JsonObject().apply {
                add("criteria", criteria as JsonElement)
                add("display", display as JsonElement);
            } as JsonElement)
        }


    fun play(p: Player) {
        val advancement = Bukkit.getAdvancement(key)
        val progress: AdvancementProgress = p.getAdvancementProgress(advancement!!)
        progress.remainingCriteria.forEach {
            progress.awardCriteria(it)
        }
        20L.rl {
            val unAdvancement = Bukkit.getAdvancement(key)
            val unProgress: AdvancementProgress = p.getAdvancementProgress(unAdvancement!!)
            unProgress.awardedCriteria.forEach {
                unProgress.revokeCriteria(it)
            }
            Bukkit.getUnsafe().removeAdvancement(key);
            Bukkit.reloadData()
        }
    }
}