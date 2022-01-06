package cn.joestar.dbcreator

import android.util.Log
import androidx.annotation.VisibleForTesting
import cn.joestar.database.NexomonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream


class MainRepository(
    private val convertor: Convertor = Convertor(),
    private val context: DbContext = DbContextImpl()
) {
    suspend fun init() {
        withContext(Dispatchers.IO) {
            createDb()
        }
    }

    private fun createDb() {
        val dao = context.getDao()
        createMonsters(dao)
        createLocations(dao)
        createRelations(dao)
    }

    @VisibleForTesting
    fun createRelations(dao: NexomonDao) {
        val name = "relations.json"
        writeDb(
            context.open(name), dao
        ) { d, l ->
            val list = convertor.convertRelation(l)
            val count = d.addMonsterLocation(list).size
            logInsert(name, list.size, count)
        }
    }

    @VisibleForTesting
    fun createLocations(dao: NexomonDao) {
        val name = "locations.json"
        writeDb(
            context.open(name), dao
        ) { d, l ->
            val locations = convertor.convertLocation(l)
            val count = d.addLocations(locations).size
            logInsert(name, locations.size, count)
        }
    }

    @VisibleForTesting
    fun createMonsters(dao: NexomonDao) {
        val name = "monsters.json"
        writeDb(
            context.open(name), dao
        ) { d, l ->
            val monsters = convertor.convertMonster(l)
            val count = d.addMonsters(monsters).size
            logInsert(name, monsters.size, count)
        }
    }

    fun logInsert(file: String, size: Int, count: Int) {
        Log.d("Tag", "convert $file total $size  insert $count")
    }

    private fun writeDb(
        stream: InputStream,
        dao: NexomonDao,
        action: (NexomonDao, List<String>) -> Unit
    ) {
        val list = convertor.readJson(stream)
        action(dao, list)
        stream.close()
    }
}

interface DbContext {
    fun getDao(): NexomonDao
    fun open(name: String): InputStream
}

class DbContextImpl : DbContext {
    override fun getDao(): NexomonDao = DbManager.getDao()

    override fun open(name: String): InputStream = DbManager.getContext().assets.open(name)
}

