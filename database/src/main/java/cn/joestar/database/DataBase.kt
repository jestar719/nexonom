package cn.joestar.database

import androidx.room.*
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

    @Query("SELECT * FROM Monster WHERE monsterId =:id")
    fun getMonsters(id: Int): Flow<DetailMonster>

    @Query("SELECT * FROM Monster")
    fun getAllMonster(): Flow<List<Monster>>

    @Query("SELECT * FROM Location WHERE locationId=:id")
    fun getDetailLocation(id: Int): Flow<DetailLocation>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addMonsters(monsters: List<Monster>): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addLocations(locations: List<Location>): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addMonsterLocation(list: List<MonsterLocation>): List<Long>
}