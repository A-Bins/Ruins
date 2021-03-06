package com.bins.ruins.scavengers.structure.classes

import com.bins.ruins.Ruins.Companion.r
import com.bins.ruins.structure.objects.utilities.Receiver.Companion.bb
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

class Path(val start: Location, val goal: Location) {
    companion object {
        val Location.isObstructed: Boolean
            get() = block.type.isSolid
        val Location.canStandAt: Boolean
            get() = !(isObstructed || (clone().add(0.0, 1.0, 0.0)).isObstructed || !(clone().add(0.0, -1.0, 0.0)).isObstructed);

        fun Location.distanceTo(loc2: Location): Double {
            if (world !== loc2.world) return Double.MAX_VALUE
            val deltaX = abs(x - loc2.x)
            val deltaY = abs(y - loc2.y)
            val deltaZ = abs(z - loc2.z)

            // euclidean distance
            val distance2d = sqrt(deltaX * deltaX + deltaZ * deltaZ)
            return sqrt(distance2d * distance2d + deltaY * deltaY)

            // manhattan distance
            //return deltaX + deltaY + deltaZ;
        }

    }
    val startNode = Node(start,0.0)
    var endNode = Node(goal,0.0)
    val open: ArrayList<Node> = arrayListOf()
    val close: ArrayList<Node> = arrayListOf()
    var testCount = 10000
    private var pathFound = false
    fun goalToWay(): Array<Location?> {
        // check if player could stand at start and endpoint, if not return empty path
        if(!start.canStandAt || !goal.canStandAt) return emptyArray<Location?>().also { "can't stand at here".bb() }

        open.add(startNode);

        // cycle through untested nodes until an exit condition is fulfilled
        while(close.size < testCount && !pathFound && open.size > 0){
            var n = open[0]
            for (nt in open) if (nt.estimatedFinalValue() < n.estimatedFinalValue()) n = nt
            if (n.estimatedExpenseLeft < 1) {
                pathFound = true
                endNode = n
            }

            n.reachableLocations()
            open.remove(n)
            close.add(n)

        }
        // returning if no path has been found

        // returning if no path has been found
        if (!pathFound) return emptyArray<Location?>().also { "not path found".bb() }


        // get length of path to create array, 1 because of start

        // get length of path to create array, 1 because of start
        var length = 1
        var n = endNode
        while (n.origin != null) {
            n = n.origin!!
            length++
        }

        //fill Array

        val locations = arrayOfNulls<Location>(length)

        //fill Array
        n = endNode
        for (i in length - 1 downTo 1) {
            locations[i] = n.loc
            n = n.origin!!
        }

        locations[0] = startNode.loc

        return locations
    }

    private fun Location.node(): Node {
        val test = Node(this, 0.0, null)
        for (n in close) if (n.id == test.id) return n
        return test
    }




    inner class Node(val loc: Location, var value: Double, var origin: Node? = null) {
        val id: UUID = UUID.randomUUID()
        var estimatedExpenseLeft = -1.0

        fun estimatedFinalValue(): Double {
            if (estimatedExpenseLeft == -1.0) estimatedExpenseLeft = loc.distanceTo(goal)
//                .also {
//                r {
//                    if (loc.getNearbyEntitiesByType(ArmorStand::class.java, 0.5, 0.5, 0.5).isEmpty())
//                        loc.world.spawn(loc, ArmorStand::class.java) {
//                            it.setGravity(false)
//                            it.isSmall = true
//                        }
//                }
//            }

            return value + 3 * estimatedExpenseLeft
        }

        // ---
        // PATHFINDING
        // ---

        // ---
        // PATHFINDING
        // ---
        private val w = Bukkit.getWorld("world")!!
        fun reachableLocations() {
            //trying to get all possibly walkable blocks
            for (x in -1..1) for (z in -1..1) if (!(x == 0 && z == 0) && x * z == 0) {
                val loc = Location(w, loc.x + x.toDouble(), loc.y, loc.z + z.toDouble())


                if (loc.canStandAt) reachNode(loc, value + 1)

                // one block up
                if (!loc.clone()
                        .add((-x).toDouble(), 2.0, (-z).toDouble()).isObstructed
                ) // block above current tile, thats why subtracting x and z
                {
                    val nLoc = loc.clone().add(0.0, 1.0, 0.0)
                    if (nLoc.canStandAt) reachNode(nLoc, value + 1.4142)
                }

                // one block down or falling multiple blocks down

                // one block down or falling multiple blocks down
                if (!loc.clone().add(0.0, 1.0, 0.0).isObstructed) {
                    val nLoc = loc.clone().add(0.0, -1.0, 0.0)
                    if (nLoc.canStandAt) // one block down
                        reachNode(nLoc, value + 1.4142) else if (!nLoc.isObstructed && !nLoc.clone()
                            .add(0.0, 1.0, 0.0).isObstructed)
                     // fall
                    {
                        var drop = 1
                        while (drop <= 2 && !loc.clone().add(0.0, -drop.toDouble(), 0.0).isObstructed) {
                            val locF = loc.clone().add(0.0, -drop.toDouble(), 0.0)
                            if (locF.canStandAt) {
                                val fallNode = addFallNode(loc, value + 1)
                                fallNode.reachNode(locF, value + drop * 2)
                            }
                            drop++
                        }
                    }
                }

            }
        }

        fun reachNode(locThere: Location, valueThere: Double) {
            val nt = locThere.node()
            if (nt.origin == null && nt !== startNode) // new node
            {
                nt.value = valueThere
                nt.origin = this
                open.add(nt)
                return
            }

            // no new node
            if (nt.value > valueThere) // this way is faster to go there
            {
                nt.value = valueThere
                nt.origin = this
            }
        }

        fun addFallNode(loc: Location?, value: Double): Node {
            return Node(loc!!, value, this)
        }
    }
}