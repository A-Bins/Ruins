package com.bins.ruins.utilities

import com.bins.ruins.Ruins
import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import java.time.LocalDateTime


@Suppress("DEPRECATION")
object ScoreBoards {
    private fun getEntryFromScore(o: Objective?, score: Int): String? {
        if (o == null) return null
        if (!hasScoreTaken(o, score)) return null
        for (s in o.scoreboard!!.entries) {
            if (o.getScore(s!!).score == score) return o.getScore(s).entry
        }
        return null
    }

    private fun hasScoreTaken(o: Objective, score: Int): Boolean {
        for (s in o.scoreboard!!.entries) {
            if (o.getScore(s!!).score == score) return true
        }
        return false
    }

    private fun replaceScore(o: Objective, score: Int, name: String) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equals(name, ignoreCase = true)) return
            if (!getEntryFromScore(o, score).equals(name, ignoreCase = true)) o.scoreboard!!.resetScores(getEntryFromScore(o, score)!!)
        }
        o.getScore(name).score = score
    }
    fun showScoreboard(p: Player) {
        if (p.scoreboard == Ruins.instance.server.scoreboardManager.mainScoreboard) p.scoreboard = Ruins.instance.server.scoreboardManager.newScoreboard
        /*val score = p.scoreboard
        val objective = if (score.getObjective(p.name) == null) score.registerNewObjective(p.name, "dummy") else score.getObjective(p.name)
        objective!!.displayName = "abcdefg"
        replaceScore(objective, 8, "${LocalDateTime.now().second}")
        if (objective.displaySlot != DisplaySlot.SIDEBAR) objective.displaySlot = DisplaySlot.SIDEBAR
        p.scoreboard = score*/
    }
}