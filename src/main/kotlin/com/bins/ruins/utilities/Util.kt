package com.bins.ruins.utilities

import com.bins.ruins.Ruins
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or

object Util {

    fun getTargetedItemEntity(p: Player) : Item?{
        for(i in 0..35) {
            val loc : Location= p.eyeLocation.add(p.location.direction.multiply(i.toDouble()/10))
            val list = loc.world.getNearbyEntitiesByType(Item::class.java, loc, 0.1, 0.1, 0.1)
            for (e in list) {
                return e
            }
        }
        return null
    }

    fun bb(str : Any){
        Bukkit.broadcastMessage("$str")
    }
    fun save(ruins: Ruins, hash: HashMap<*, *>, jsonname: String) {
        val data = JSONObject()
        hash.forEach { (key: Any, value: Any) -> data[key] = "" + value }
        try {
            val UniOutput = BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(File(ruins.dataFolder, "$jsonname.json").path),
                    "EUC-KR"
                )
            )
            UniOutput.write("$data")
            UniOutput.flush()
            UniOutput.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun serializeItemStack(item: ItemStack): String? {
        val outputStream = ByteArrayOutputStream()
        try {
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeObject(item)
            dataOutput.close()
            return Base64Coder.encodeLines(outputStream.toByteArray())
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return null
    }
    fun deserializeItemStack(item: String): ItemStack? {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(item))
        try {
            val dataInput = BukkitObjectInputStream(inputStream)
            dataInput.close()
            return dataInput.readObject() as ItemStack
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }
    private fun writeJson(ruins: Ruins, jsonname: String, write: String) {
        try {
            val UniOutput = BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(File(ruins.dataFolder, "$jsonname.json").path),
                    "EUC-KR"
                )
            )
            UniOutput.write(write)
            UniOutput.flush()
            UniOutput.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun loadInt(ruins: Ruins, hash: HashMap<UUID, Int>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val jsonObject: JSONObject = obj as JSONObject
                jsonObject.forEach { key: Any, value: Any ->
                    hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toInt()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun saveItemStack(ruins: Ruins, hash: HashMap<*, ItemStack>, jsonname: String) {
        val data = JSONObject()
        hash.forEach { (key: Any, value: ItemStack?) -> data[key] = serializeItemStack(value) }
        try {
            val UniOutput = BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(File(ruins.dataFolder, "$jsonname.json").path),
                    "EUC-KR"
                )
            )
            UniOutput.write("$data")
            UniOutput.flush()
            UniOutput.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun loadItemStack(ruins: Ruins, hash: HashMap<UUID, ItemStack>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val jsonObject: JSONObject = obj as JSONObject
                jsonObject.forEach { key: Any, value: Any ->
                    hash[UUID.fromString(key.toString() + "")] = deserializeItemStack("$value")!!
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun loadloc(ruins: Ruins, hash: HashMap<UUID, Location>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
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
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun loadDouble(ruins: Ruins, hash: HashMap<UUID, Double>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val jsonObject: JSONObject = obj as JSONObject
                jsonObject.forEach{ key: Any, value: Any ->
                    hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toDouble()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun loadString(ruins: Ruins, hash: HashMap<UUID, String>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val jsonObject: JSONObject = obj as JSONObject
                jsonObject.forEach { key: Any, value: Any ->
                    try {
                        hash[UUID.fromString(key.toString() + "")] = "$value"
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun saveArray(ruins: Ruins, hash: ArrayList<*>, jsonname: String) {
        val array = JSONArray()
        hash.forEach { value: Any -> array.add("" + value) }
        writeJson(ruins, jsonname, array.toJSONString())
    }
    fun loadArrayLocationString(ruins: Ruins, hash: ArrayList<String>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val array: JSONArray = obj as JSONArray
                array.forEach { value: Any -> hash.add(value.toString()) }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun loadUUID(ruins: Ruins, hash: ArrayList<UUID>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val array: JSONArray = obj as JSONArray
                array.forEach { value: Any -> hash.add(UUID.fromString("" + value)) }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    fun loadStrInt(ruins: Ruins, hash: HashMap<String, Int>, jsonname: String) {
        ruins.makeFile(File(ruins.dataFolder, "$jsonname.json"))
        File(ruins.dataFolder, "$jsonname.json").mkdir()
        val parser = JSONParser()
        try {
            if (FileReader(File(ruins.dataFolder, "$jsonname.json")).ready()) {
                val obj: Any = parser.parse(FileReader(File(ruins.dataFolder, "$jsonname.json")))
                val jsonObject: JSONObject = obj as JSONObject
                jsonObject.forEach { key: Any, value: Any ->
                    hash[key.toString() + ""] = (value.toString() + "").toInt()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

}