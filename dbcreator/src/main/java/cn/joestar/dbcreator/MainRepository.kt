package cn.joestar.dbcreator

import android.content.res.AssetManager
import cn.joestar.database.NexomonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainRepository(private val convertor: Convertor = Convertor()) {
    suspend fun init() {
        withContext(Dispatchers.IO) {
            createDb()
        }
    }

    private fun createDb() {
        val assets = DbManager.getContext().assets
        val dao = DbManager.getDao()
        createMonsters(
            assets,
            "monsters.json",
            dao
        ) { d, l -> d.addMonsters(convertor.convertMonster(l)) }
        createMonsters(
            assets,
            "location.json",
            dao
        ) { d, l -> d.addLocations(convertor.convertLocation(l)) }
        createMonsters(
            assets,
            "monsters.json",
            dao
        ) { d, l -> d.addMonsterLocation(convertor.convertRelation(l)) }
    }

    private fun createMonsters(
        assets: AssetManager,
        name: String,
        dao: NexomonDao,
        action: (NexomonDao, List<String>) -> Unit
    ) {
        val open = assets.open(name)
        val list = convertor.readJson(open)
        action(dao, list)
        open.close()
    }
}