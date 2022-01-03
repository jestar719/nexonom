package cn.joestar.dbcreator

import android.content.res.AssetManager
import cn.joestar.database.Location
import cn.joestar.database.Monster
import cn.joestar.database.MonsterLocation
import cn.joestar.database.NexomonDao
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader


class MainRepository {
    suspend fun init() {
        withContext(Dispatchers.IO) {
            createDb()
        }
    }

    private fun createDb() {
        val assets = DbManager.getContext().assets
        val dao = DbManager.getDao()
        createMonsters(assets, "monsters.json", dao) { d, l -> d.addMonsters(convertMonster(l)) }
        createMonsters(assets, "location.json", dao) { d, l -> d.addLocations(convertLocation(l)) }
        createMonsters(assets, "monsters.json", dao) { d, l ->
            d.addMonsterLocation(
                convertRelation(
                    l
                )
            )
        }
    }

    private fun convertRelation(l: List<String>): List<MonsterLocation> {
        return getSequence(l).map {
            MonsterLocation(
                monsterId = it[0].toInt(),
                locationId = it[1].toInt(),
                landsId = it[2].toInt()
            )
        }.toList()
    }

    private fun convertLocation(l: List<String>): List<Location> {

        return getSequence(l).map {
            Location(
                locationId = it[0].toInt(),
                name = it[1],
                landsId = it[2].toInt(),
                lands = if (it.size > 3) it[3] else ""
            )
        }.toList()
    }

    private fun convertMonster(l: List<String>): List<Monster> {
        return getSequence(l).map {
            Monster(
                monsterId = it[0].toInt(),
                name = it[0],
                type = it[1],
                rare = it[2],
                other = if (it.size > 3) it[3] else ""
            )
        }.toList()
    }

    private fun getSequence(list: List<String>) = list.asSequence().map { it.split(",") }

    private fun createMonsters(
        assets: AssetManager,
        name: String,
        dao: NexomonDao,
        action: (NexomonDao, List<String>) -> Unit
    ) {
        val open = assets.open(name)
        val list = Gson().fromJson<List<String>>(InputStreamReader(open), List::class.java)
        action(dao, list)
        open.close()
    }
}