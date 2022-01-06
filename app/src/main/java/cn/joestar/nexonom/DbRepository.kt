package cn.joestar.nexonom

import android.content.Context
import androidx.room.Room
import cn.joestar.database.DB_NAME
import cn.joestar.database.NexomonDatabase

object DbRepository {
    lateinit var db: NexomonDatabase
    fun initDb(context: Context) {
        db = Room.databaseBuilder(context, NexomonDatabase::class.java, DB_NAME)
            .createFromAsset(DB_NAME)
            .build()
    }

}