package cn.joestar.nexonom

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment.DIRECTORY_DOCUMENTS
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import cn.joestar.database.DB_NAME
import cn.joestar.database.Monster
import cn.joestar.database.NexomonDao
import cn.joestar.database.NexomonDatabase
import cn.joestar.nexonom.entity.Monsters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

object DbRepository {
    private lateinit var db: NexomonDatabase
    private const val spName = "nexonomSp"
    private const val collectSequence = "collectSequence"
    private lateinit var sp: SharedPreferences
    private lateinit var sequence: StringBuilder
    private lateinit var app: Application

    fun initDb(context: Application) {
        app = context
        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(context, NexomonDatabase::class.java, DB_NAME)
                .createFromAsset(DB_NAME.plus(".db"))
                .build()
        }
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val sq = sp.getString(collectSequence, null)
        sequence = if (sq == null) getDefaultSequence() else StringBuilder().append(sq)
    }

    @VisibleForTesting
    fun getDefaultSequence(): StringBuilder {
        val builder = StringBuilder()
        repeat(400) {
            builder.append("0")
        }
        return builder
    }

    private val dao: NexomonDao
        get() = db.getDao()

    fun getMonsters(): Flow<List<Monster>> {
        return dao.getAllMonster().onEach {
            it.forEach(this::setCollection)
        }
    }

    private const val selectChar = '1'

    private fun setCollection(monster: Monster) {
        monster.apply {
            isCollect = sequence[monsterId] == selectChar
        }
    }

    fun setSelect(id: Int) {
        sequence.replace(id, id + 1, selectChar.toString())
        val toString = sequence.toString()
        saveSequence(toString)
    }

    private fun saveSequence(toString: String) {
        sp.edit().putString(collectSequence, toString).apply()
    }

    fun getLands() = dao.getLocations()
    fun getLocations(locationId: Int = 0) = dao.getLocations(locationId)
    fun getDetailLocation(locationId: Int) = dao.getDetailLocation(locationId).map {
        val list = it.monsters
        list.forEach(this::setCollection)
        Monsters(it.location.name, list)
    }

    fun get() {
        dao.getDetailLocation(1).map {
            val list = it.monsters
            list.forEach(this::setCollection)
            Monsters(it.location.name, list)
        }
    }

    fun exportSequence() {
        getLocalFile().writeText(sequence.toString())
    }

    private fun getLocalFile() = File(app.getExternalFilesDir(DIRECTORY_DOCUMENTS), collectSequence)

    fun importSequence() {
        val localFile = getLocalFile()
        if (localFile.exists()) {
            val str = localFile.readText()
            sequence = StringBuilder(str)
            saveSequence(str)
        }
    }
}