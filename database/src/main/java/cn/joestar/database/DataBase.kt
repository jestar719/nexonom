package cn.joestar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

const val DB_NAME = "NexomonDb"
const val DB_VERSION = 1


@androidx.room.Database(
    entities = [Monster::class, Location::class, MonsterLocation::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class NexomonDatabase : RoomDatabase() {
    abstract fun getDao(): NexomonDao
}

@Dao
interface NexomonDao {
    @Query("SELECT * FROM Location WHERE landsId=:id")
    fun getLocations(id: Int = 0): Flow<List<Location>>

    @Query("SELECT * FROM Monster WHERE monsterId in (:ids)")
    fun getMonsters(ids: List<Int>): Flow<List<DetailMonster>>

    @Query("SELECT * FROM Location WHERE locationId=(:ids)")
    fun getDetailLocation(ids: List<Int>): Flow<List<DetailLocation>>

    @Insert
    fun addMonsters(monsters: List<Monster>)

    @Insert
    fun addLocations(locations: List<Location>)

    @Insert
    fun addMonsterLocation(list: List<MonsterLocation>)
}