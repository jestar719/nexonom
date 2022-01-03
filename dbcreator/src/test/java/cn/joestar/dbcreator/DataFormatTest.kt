package cn.joestar.dbcreator

import cn.joestar.database.ItemAble
import cn.joestar.database.Location
import cn.joestar.database.Monster
import cn.joestar.database.MonsterLocation
import com.google.gson.Gson
import org.junit.Test
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.collections.HashMap


class DataFormatTest {
    private val root = File("../RawData")
    val target = File("../assets")
    private val locationRank = arrayOf(
        "Orphanage",
        "Palmaya",
        "Haunted Woods",
        "Immortal Citadel",
        "Outlands",
        "Frozen Lake Cave",
        "Outlands Mine",
        "Drake Isles",
        "Frozen Tundra",
        "Desert",
        "Lateria",
        "Cursed Cave",
        "Ignitia"
    )

    private val rareMap = mapOf(
        "Common" to "N",
        "Uncommon" to "S",
        "Rare" to "R",
        "Mega Rare" to "SR",
        "Ultra Rare" to "SSR",
    )

    private val typeMap = mapOf(
        "Water" to "水",
        "Fire" to "火",
        "Mineral" to "地",
        "Wind" to "风",
        "Grass" to "草",
        "Electric" to "电",
        "Plant" to "草",
        "Ghost" to "鬼",
        "Psychic" to "超",
        "Normal" to "普",
        "Water Drag" to "水龙",
        "Fire Drag" to "火龙",
        "Mineral Drag" to "地龙",
        "Wind Drag" to "风龙",
        "Grass Drag" to "草龙",
        "Electric Drag" to "电龙",
        "Plant Drag" to "草龙",
        "Ghost Drag" to "鬼龙",
        "Psychic Drag" to "超龙",
        "Normal Drag" to "普龙",
        "Starter" to "初始",
        "Spoilered" to ""
    )

    @Test
    fun convert() {
        val locations = HashMap<Int, Location>()
        val monsters = HashMap<Int, Monster>()
        val relations = LinkedList<MonsterLocation>()
        root.listFiles().asSequence().forEach {
            val name = it.nameWithoutExtension
            val id = locationRank.indexOf(name) + 1
            onLocationSame(locations, id, name)
            locations[id] = Location(id, name, 0)
            it.listFiles().forEachIndexed { index, file ->
                val lId = id * 100 + index
                val lName = file.nameWithoutExtension
                onLocationSame(locations, lId, lName)
                locations[lId] = Location(lId, lName, id, name)
                FileReader(file).readLines().asSequence().map { s ->
                    createMonsters(s)
                }.forEach { monster ->
                    val key = monster.monsterId
                    if (!monsters.containsKey(key)) {
                        monsters[key] = monster
                    }
                    relations.add(MonsterLocation(key, lId, id))
                }
            }
        }
        writeItemAble("locations.json", locations.values)
        writeItemAble("monsters.json", monsters.values)
        relations.sort()
        writeJson("relations.json", relations)
    }

    private fun writeItemAble(name: String, list: Collection<ItemAble>) {
        val set = list.toSortedSet { a, b -> a.provideId() - b.provideId() }
        writeJson(name, set)
    }

    private fun writeJson(name: String, list: Collection<Any>) {
        val gson = Gson()
        val string = gson.toJson(list)
        File(target, name).writeText(string)
    }

    private fun createMonsters(it: String): Monster {
        val split = it.split(",")
        return Monster(
            monsterId = split[0].toInt(),
            name = split[1],
            type = typeMap[split[2]] ?: "",
            rare = rareMap[split[3]] ?: "",
            other = if (split.size > 4) typeMap[split[4]] ?: "" else ""
        )
    }

    private fun onLocationSame(
        locations: HashMap<Int, Location>,
        id: Int,
        name: String
    ) {
        if (locations.containsKey(id)) {
            throw IllegalStateException("there was same location key is $id,name is $name")
        }
    }
}