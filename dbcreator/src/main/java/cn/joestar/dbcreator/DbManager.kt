package cn.joestar.dbcreator

import android.app.Application
import androidx.room.Room
import cn.joestar.database.DB_NAME
import cn.joestar.database.NexomonDatabase


object DbManager {
    private lateinit var context: Application
    private lateinit var db: NexomonDatabase

    fun init(context: Application) {
        this.context = context
        db = Room.databaseBuilder(context, NexomonDatabase::class.java, DB_NAME).build()
    }

    fun getDao() = db.getDao()
    fun getContext() = context
}