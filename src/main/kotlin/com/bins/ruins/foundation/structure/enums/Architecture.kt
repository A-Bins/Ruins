package com.bins.ruins.foundation.structure.enums

import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.*
import org.bukkit.block.data.BlockData


enum class Architecture {
     WOOD_WALL() {
//         override fun east(): List<Pair<Pair<Material, BlockData>, Location>> {
//
//         }
//         override fun north(): List<Pair<Pair<Material, BlockData>, Location>> {
//
//         }
         override fun south(): List<Pair<Pair<Material, BlockData>, Location>> {
             return mutableListOf<Pair<Pair<Material, BlockData>, Location>>().apply {

                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 1.0, 2.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 1.0, 1.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 1.0, 0.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 1.0, -1.0, 0.0))

                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, -2.0, 2.0, 0.0))
                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, -2.0, 1.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 0.0, 2.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 0.0, 1.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 0.0, 0.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, 0.0, -1.0, 0.0))

                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, 2.0, 2.0, 0.0))
                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, 2.0, 1.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, -1.0, 2.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, -1.0, 1.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, -1.0, 0.0, 0.0))
                 add((Material.OAK_PLANKS to Material.OAK_PLANKS.createBlockData()) to Location(world, -1.0, -1.0, 0.0))
                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, 2.0, 0.0, 0.0))
                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, 2.0, -1.0, 0.0))

                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, -2.0, 0.0, 0.0))
                 add((Material.OAK_LOG to Material.OAK_LOG.createBlockData()) to Location(world, -2.0, -1.0, 0.0))

             }

         }
//         override fun west(): List<Pair<Pair<Material, BlockData>, Location>> {
//
//         }

         override fun overlap(direction: String, eyeLoc: Location, place: Boolean): Boolean {
             when(direction) {
                 "south" -> {

                     val loc = eyeLoc.clone().add(Location(world, 2.0, 2.0, 0.0))
                     val transL = arrayListOf<Location>()
                     val transR = arrayListOf<Location>()
                     var overlapLeft = 0
                     repeat(4) {
//                         world?.spawnParticle(Particle.REDSTONE, loc.clone().add(0.0, -it.toDouble(), 0.0), 5, Particle.DustOptions(
//                             Color.RED, 2F))
                         val current = loc.clone().add(0.0, -it.toDouble(), 0.0)
                         transL.add(current)
                         if (current.block.type == Material.OAK_LOG) {
                             ++overlapLeft
                         }
                     }
                     val loc2 = eyeLoc.clone().add(Location(world, -2.0, 2.0, 0.0))
                     var overlapRight = 0
                     repeat(4) {
//                         world?.spawnParticle(Particle.REDSTONE, loc2.clone().add(0.0, -it.toDouble(), 0.0), 5, Particle.DustOptions(
//                             Color.RED, 2F))
                         val current = loc2.clone().add(0.0, -it.toDouble(), 0.0)
                         transR.add(current)
                         if (current.block.type == Material.OAK_LOG) {
                             ++overlapRight
                         }
                     }
                     if (overlapLeft == 4 && overlapRight == 4) {
                         if (place) {
                             transL.forEach { it.block.type = Material.OAK_PLANKS }
                             transR.forEach { it.block.type = Material.OAK_PLANKS }
                         }
//                         "! OVERLAP! TWICE".bb()
                         return true
                     } else if (overlapLeft == 4) {
                         if (place) {
                             transL.forEach { it.block.type = Material.OAK_PLANKS }
                         }
//                         "! OVERLAP! LEFT".bb()
                         return true
                     } else if (overlapRight == 4) {
                         if (place) {
                             transR.forEach { it.block.type = Material.OAK_PLANKS }
                         }
//                         "! OVERLAP! RIGHT".bb()
                         return true
                     }
                     return false
                 }
             }
             return false
         }
         override fun follow(direction: String, eyeLoc: Location): Location {
            when(direction) {
                "south" -> {
                    repeat(6) {
                        if((eyeLoc.clone().add(it.toDouble(), 2.0, 0.0).block.type == Material.OAK_LOG) &&
                                (eyeLoc.clone().add(it.toDouble(), 1.0, 0.0).block.type == Material.OAK_LOG) &&
                                (eyeLoc.clone().add(it.toDouble(), 0.0, 0.0).block.type == Material.OAK_LOG) &&
                                (eyeLoc.clone().add(it.toDouble(), -1.0, 0.0).block.type == Material.OAK_LOG)) {
                            return@follow eyeLoc.clone().add(it.toDouble()-2, 0.0, 0.0)
                        }
                        else if((eyeLoc.clone().add(-it.toDouble(), 2.0, 0.0).block.type == Material.OAK_LOG) &&
                            (eyeLoc.clone().add(-it.toDouble(), 1.0, 0.0).block.type == Material.OAK_LOG) &&
                            (eyeLoc.clone().add(-it.toDouble(), 0.0, 0.0).block.type == Material.OAK_LOG) &&
                            (eyeLoc.clone().add(-it.toDouble(), -1.0, 0.0).block.type == Material.OAK_LOG)) {

                            return@follow eyeLoc.clone().add(-it.toDouble()+2, 0.0, 0.0)
                        }
                    }
                    return eyeLoc
                }
            }
             return eyeLoc
         }
         override fun build(direction: String, eyeLoc: Location) {
             if (canBuild(direction, eyeLoc)) {
                 when (direction) {
                     "south" -> {
                         var everOver = false
                         south().forEach {
                             val loc = it.second.add(eyeLoc)
                             if(loc.block.type.isAir) {
                                 loc.block.type = it.first.first
                                 loc.block.blockData = it.first.second
                             }else if(!everOver) {
                                 overlap(direction, eyeLoc, true)
                                 everOver = true
                             }
                         }
                     }
                 }
             }
         }
         override fun canBuild(direction: String, playerLocation: Location): Boolean {
             val left = (playerLocation.clone().add(2.0, 2.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(2.0, 1.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(2.0, 0.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(2.0, -1.0, 0.0).block.type == Material.AIR)
             val right = (playerLocation.clone().add(-2.0, 2.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-2.0, 1.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-2.0, 0.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-2.0, -1.0, 0.0).block.type == Material.AIR)
             val center =
                 (playerLocation.clone().add(-1.0, 2.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-1.0, 1.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-1.0, 0.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(-1.0, -1.0, 0.0).block.type == Material.AIR) &&

                 (playerLocation.clone().add(0.0, 2.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(0.0, 1.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(0.0, 0.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(0.0, -1.0, 0.0).block.type == Material.AIR) &&

                     (playerLocation.clone().add(1.0, 2.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(1.0, 1.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(1.0, 0.0, 0.0).block.type == Material.AIR) &&
                     (playerLocation.clone().add(1.0, -1.0, 0.0).block.type == Material.AIR)
             val floor =
                 (playerLocation.clone().add(1.0, -2.0, 0.0).block.type != Material.AIR) ||
                     (playerLocation.clone().add(2.0, -2.0, 0.0).block.type != Material.AIR) ||
                     (playerLocation.clone().add(0.0, -2.0, 0.0).block.type != Material.AIR) ||
                     (playerLocation.clone().add(-1.0, -2.0, 0.0).block.type != Material.AIR) ||
                     (playerLocation.clone().add(-2.0, -2.0, 0.0).block.type != Material.AIR)
             if(center) {
                 if(overlap(direction, playerLocation, false)){
                     return true
                 }
             }
             return left && right && center && floor
         }
     };
    val world = Bukkit.getWorld("world")
//    abstract fun east(): List<Pair<Pair<Material, BlockData>, Location>>
//    abstract fun west(): List<Pair<Pair<Material, BlockData>, Location>>
//    abstract fun north(): List<Pair<Pair<Material, BlockData>, Location>>
    abstract fun south(): List<Pair<Pair<Material, BlockData>, Location>>
    abstract fun build(direction: String, eyeLoc: Location)
    abstract fun overlap(direction: String, eyeLoc: Location, place: Boolean): Boolean
    abstract fun canBuild(direction: String, playerLocation: Location): Boolean
    abstract fun follow(direction: String, eyeLoc: Location): Location
}