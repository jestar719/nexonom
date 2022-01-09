package cn.joestar.nexonom

import android.content.Context
import androidx.room.Room
import cn.joestar.database.DB_NAME
import cn.joestar.database.Monster
import cn.joestar.database.NexomonDao
import cn.joestar.database.NexomonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object DbRepository {
    private lateinit var db: NexomonDatabase
    fun initDb(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(context, NexomonDatabase::class.java, DB_NAME)
                .createFromAsset(DB_NAME.plus(".db"))
                .build()
        }
    }

    private val dao: NexomonDao
        get() = db.getDao()

    fun getMonsters(): Flow<List<Monster>> {
        return dao.getAllMonster().onEach {
            it.forEach { monster ->
                monster.isCollect = isCollect(monster)
            }
        }
    }

    private fun isCollect(monster: Monster): Boolean {
        return false
    }

    fun getLands() = dao.getLocations()

    fun getLocations(locationId: Int = 0) = dao.getLocations(locationId)
    fun getDetailLocation(locationId: Int) = dao.getDetailLocation(locationId)

}