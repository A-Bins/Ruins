package com.bins.ruins.structure.objects.utilities

import com.bins.ruins.Ruins
import com.bins.ruins.cherryblossom.classes.Auth
import com.bins.ruins.structure.classes.Stash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.structure.enums.types.Receiver
import com.bins.ruins.structure.enums.types.Receiver.*
import com.bins.ruins.structure.objects.utilities.Receiver.deserializeItemStack
import com.bins.ruins.structure.objects.utilities.Receiver.serializeItemStack
import com.bins.ruins.structure.objects.utilities.Receiver.tryCast
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
object Util {
    private fun writeJson(ruins: Ruins, name: String, write: String) {
        val uniOutput = BufferedWriter(
            OutputStreamWriter(
                FileOutputStream(File(ruins.dataFolder, "$name.json").path),
                "EUC-KR"
            )
        )
        uniOutput.write(write)
        uniOutput.flush()
        uniOutput.close()
    }
    /**
     * @return item can be given if return value is -1, then return amount that can't be given if return value >= 1 also return -2 if currentItemStack's type is AIR
     **/
    fun isGiven(p: Player, currentItemStack: ItemStack): Int{
        if(currentItemStack.type == Material.AIR)
            return -2
        when(p.inventory.firstEmpty() == -1){
            true  -> {
                val list: ArrayList<Int> = ArrayList()
                for (i in p.inventory.contents) {
                    if (i == null)
                        continue
                    val item = i.clone()
                    item.amount = currentItemStack.amount
                    if ((item == currentItemStack) and (i.amount != 64))
                        list.add(i.amount)
                }


                var bool = false
                var over = currentItemStack.clone().amount

                for (i in list) {
                    if (over != (currentItemStack.amount))
                        continue
                    if ((i + currentItemStack.amount) <= 64)
                        bool = true
                    else
                        over = (i + currentItemStack.amount) - 64
                }
                return if(bool) -1 else over
            }
            false -> return -1
        }

    }
    fun save(ruins: Ruins, hash: HashMap<*, *>, name: String, receiver: Receiver) {

        when (receiver) {
            STASH -> {
                hash.tryCast<HashMap<UUID, Stash>> {
                    val main = JSONObject()

                    this.values.forEach { s ->
                        val everything = JSONArray()
                        s.drawers.forEach { v ->
                            val items = JSONObject()
                            val drawerObj = JSONObject()
                            v.items.forEach { (k, v) ->
                                items[k] = v?.serializeItemStack()
                            }
                            drawerObj["items"] = items
                            drawerObj["unlocked"] = v.unlocked
                            drawerObj["unlockState"] = v.unlockState
                            everything.add(drawerObj)
                        }
                        main[s.uuid] = everything
                    }
                    writeJson(ruins, name, main.toJSONString())
                }
            }
            ITEMSTACK -> {
                hash.tryCast<HashMap<UUID, ItemStack>> {
                    val data = JSONObject()
                    this.forEach { (key: Any, value: ItemStack) -> data[key] = value.serializeItemStack() }
                    writeJson(ruins, name, data.toJSONString())
                }
            }
            TOTAL -> {
                hash.tryCast<HashMap<*, Total>> {
                    val obj = JSONObject()
                    this.forEach { (key: Any, value: Total) -> obj[key] = "${value.k}, ${value.d}" }
                    writeJson(ruins, name, obj.toJSONString())
                }
            }
            AUTH -> {
                hash.tryCast<HashMap<*, UUID>> {
                    val obj = JSONObject()
                    this.forEach { (key: Any, value: UUID) -> obj[key] = "$value" }
                    writeJson(ruins, name, obj.toJSONString())
                }
            }
            else -> {
                val data = JSONObject()
                hash.forEach { (key: Any, value: Any) -> data[key] = "" + value }
                writeJson(ruins, name, data.toJSONString())

            }
        }
    }
    fun load(ruins: Ruins, hash: HashMap<*, *>, name: String, receiver: Receiver) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        when (receiver) {
            STASH -> {
                hash.tryCast<HashMap<UUID, Stash>> {
                    if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                        val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                        var unlockState: Int
                        var unlocked: Boolean
                        obj.tryCast<JSONObject>{
                            forEach {
                                val drawers: ArrayList<Stash.Drawer> = arrayListOf()
                                it.value!!.tryCast<JSONArray> {
                                    forEach { v ->
                                        v!!.tryCast<JSONObject> {
                                            unlockState = "${this["unlockState"]}".toInt()
                                            unlocked = "${this["unlocked"]}".toBoolean()
                                            this["items"]!!.tryCast<JSONObject>{
                                                val items: HashMap<Int, ItemStack?> = HashMap()
                                                this.forEach { (v2, v3) ->
                                                    items["$v2".toInt()] = if("$v3" == "null") null else "$v3".deserializeItemStack()
                                                }
                                                drawers.add(
                                                    Stash.Drawer(
                                                        items,
                                                        unlockState = unlockState,
                                                        unlocked = unlocked
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                                Stash.create(UUID.fromString("${it.key}"), *drawers.toTypedArray())
                            }
                        }
                    }
                }
            }
            ITEMSTACK -> {

                hash.tryCast<HashMap<UUID, ItemStack>> {
                    if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                        val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                        val jsonObject: JSONObject = obj as JSONObject
                        jsonObject.forEach { key: Any, value: Any ->
                            this[UUID.fromString(key.toString() + "")] = "$value".deserializeItemStack()
                        }
                    }
                }
            }
            INT -> {
                hash.tryCast<HashMap<String, Int>> {
                    if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                        val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                        val jsonObject: JSONObject = obj as JSONObject
                        jsonObject.forEach { key: Any, value: Any ->
                            this[key.toString() + ""] = (value.toString() + "").toInt()
                        }
                    }
                }
            }
            TOTAL -> {
                if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    hash.tryCast<HashMap<UUID, Total>>{
                        jsonObject.forEach { key: Any, value: Any ->
                            this[UUID.fromString("$key")] = Total.create(
                                (value.toString().split(", ")[0].toInt()),
                                (value.toString().split(", ")[1].toInt())
                            )


                        }
                    }
                }
            }
            AUTH -> {
                if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        Auth.completers["$key".toLong()] = UUID.fromString("$value")
                    }
                }
            }
        }
    }
    /*


    fun loadInt(ruins: Ruins, hash: HashMap<UUID, Int>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val jsonObject: JSONObject = obj as JSONObject
            jsonObject.forEach { key: Any, value: Any ->
                hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toInt()
            }
        }
    }
    fun loadLoc(ruins: Ruins, hash: HashMap<UUID, Location>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val jsonObject: JSONObject = obj as JSONObject
            jsonObject.forEach { key: Any, value: Any ->
                hash[UUID.fromString(key.toString() + "")] = Location(
                    Bukkit.getWorld(
                        value.toString().split("name=").toTypedArray()[1].split("}").toTypedArray()[0]
                    ),
                    value.toString().split("x=").toTypedArray()[1].split(",")
                        .toTypedArray()[0].toDouble(),
                    value.toString().split("y=").toTypedArray()[1].split(",")
                        .toTypedArray()[0].toDouble(),
                    value.toString().split("z=").toTypedArray()[1].split(",")
                        .toTypedArray()[0].toDouble()
                )
            }
        }
    }
    fun loadDouble(ruins: Ruins, hash: HashMap<UUID, Double>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val jsonObject: JSONObject = obj as JSONObject
            jsonObject.forEach{ key: Any, value: Any ->
                hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toDouble()
            }
        }
    }
    fun loadString(ruins: Ruins, hash: HashMap<UUID, String>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val jsonObject: JSONObject = obj as JSONObject
            jsonObject.forEach { key: Any, value: Any ->
                hash[UUID.fromString(key.toString() + "")] = "$value"
            }
        }
    }
    fun saveArray(ruins: Ruins, hash: ArrayList<*>, name: String) {
        val array = JSONArray()
        hash.forEach { value: Any -> array.add("" + value) }
        writeJson(ruins, name, array.toJSONString())
    }
    fun loadArrayLocationString(ruins: Ruins, hash: ArrayList<String>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val array: JSONArray = obj as JSONArray
            array.forEach { value: Any -> hash.add(value.toString()) }
        }
    }
    fun loadUUID(ruins: Ruins, hash: ArrayList<UUID>, name: String) {
        ruins.makeFile(File(ruins.dataFolder, "$name.json"))
        File(ruins.dataFolder, "$name.json").mkdir()
        val parser = JSONParser()
        if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
            val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
            val array: JSONArray = obj as JSONArray
            array.forEach { value: Any -> hash.add(UUID.fromString("" + value)) }
        }
    }
    */
}