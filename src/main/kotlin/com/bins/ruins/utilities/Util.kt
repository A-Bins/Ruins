package com.bins.ruins.utilities

import com.bins.ruins.Ruins
import com.bins.ruins.structure.classes.Strash
import com.bins.ruins.structure.classes.Total
import com.bins.ruins.utilities.Inlines.tryCast
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.*
import java.util.*
import kotlin.collections.HashMap
@Suppress("DEPRECATION")
object Util {
    val Player.targetedItemEntity: Item?
        get() {
            for(i in 0..35) {
                val loc : Location= this.eyeLocation.add(this.location.direction.multiply(i.toDouble()/10))
                val list = loc.world.getNearbyEntitiesByType(Item::class.java, loc, 0.15, 0.15, 0.15)
                for (e in list) {
                    return e
                }
            }
            return null
        }
    fun bb(str : Any){
        Bukkit.getServer().sendMessage(Component.text("$str"))
    }
    fun deserializeItemStack(item: String): ItemStack {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(item))
        val dataInput = BukkitObjectInputStream(inputStream)
        dataInput.close()
        return dataInput.readObject() as ItemStack
    }
    fun serializeItemStack(item: ItemStack): String {
        val outputStream = ByteArrayOutputStream()
        val dataOutput = BukkitObjectOutputStream(outputStream)
        dataOutput.writeObject(item)
        dataOutput.close()
        return Base64Coder.encodeLines(outputStream.toByteArray())
    }
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
    fun save(ruins: Ruins, hash: HashMap<*, *>, name: String) {

        when{

            hash.values.stream().anyMatch {a -> a is Strash } -> {
                hash.tryCast<HashMap<UUID, Strash>> {
                    val data = JSONObject()
                    this.values.forEach { s -> data[s.uuid] = "unlockProgress: ${s.unlockProgress}; drawers: ${s.drawers};  " }
                    val uniOutput = BufferedWriter(
                        OutputStreamWriter(
                            FileOutputStream(File(ruins.dataFolder, "$name.json").path),
                            "EUC-KR"
                        )
                    )
                    uniOutput.write("$data")
                    uniOutput.flush()
                    uniOutput.close()

                }
            }
            hash.values.stream().anyMatch { a -> a is ItemStack } -> {
                hash.tryCast<HashMap<UUID, ItemStack>> {
                    val data = JSONObject()
                    this.forEach { (key: Any, value: ItemStack) -> data[key] = serializeItemStack(value) }
                    val uniOutput = BufferedWriter(
                        OutputStreamWriter(
                            FileOutputStream(File(ruins.dataFolder, "$name.json").path),
                            "EUC-KR"
                        )
                    )
                    uniOutput.write("$data")
                    uniOutput.flush()
                    uniOutput.close()
                }
            }
            hash.values.stream().anyMatch { a -> a is Total } -> {
                hash.tryCast<HashMap<*, Total>> {
                    val obj = JSONObject()
                    this.forEach { (key: Any, value: Total) -> obj[key] = "${value.k}, ${value.d}" }
                    writeJson(ruins, name, obj.toJSONString())
                }
            }
            else -> {
                val data = JSONObject()
                hash.forEach { (key: Any, value: Any) -> data[key] = "" + value }
                val uniOutput = BufferedWriter(
                    OutputStreamWriter(
                        FileOutputStream(File(ruins.dataFolder, "$name.json").path),
                        "EUC-KR"
                    )
                )
                uniOutput.write("$data")
                uniOutput.flush()
                uniOutput.close()
            }
        }
    }
    fun load(ruins: Ruins, hash: HashMap<*, *>, name: String) {
        when{
            hash.values.stream().anyMatch { a -> a is ItemStack } -> {

                hash.tryCast<HashMap<UUID, ItemStack>> {
                    ruins.makeFile(File(ruins.dataFolder, "$name.json"))
                    File(ruins.dataFolder, "$name.json").mkdir()
                    val parser = JSONParser()
                    if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                        val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                        val jsonObject: JSONObject = obj as JSONObject
                        jsonObject.forEach { key: Any, value: Any ->
                            this[UUID.fromString(key.toString() + "")] = deserializeItemStack("$value")
                        }
                    }
                }
            }
            hash.values.stream().anyMatch { a -> a is Int } -> {
                hash.tryCast<HashMap<String, Int>> {
                    ruins.makeFile(File(ruins.dataFolder, "$name.json"))
                    File(ruins.dataFolder, "$name.json").mkdir()
                    val parser = JSONParser()
                    if (FileReader(File(ruins.dataFolder, "$name.json")).ready()) {
                        val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$name.json")))
                        val jsonObject: JSONObject = obj as JSONObject
                        jsonObject.forEach { key: Any, value: Any ->
                            this[key.toString() + ""] = (value.toString() + "").toInt()
                        }
                    }
                }
            }
            hash.values.stream().anyMatch { a -> a is Total } -> {

                ruins.makeFile(File(ruins.dataFolder, "$name.json"))
                File(ruins.dataFolder, "$name.json").mkdir()
                val parser = JSONParser()
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