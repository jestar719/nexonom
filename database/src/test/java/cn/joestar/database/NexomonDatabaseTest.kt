package cn.joestar.database

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NexomonDatabaseTest {

    lateinit var database: NexomonDatabase

    @Before
    fun setUp() {
        val context = getApplicationContext<Application>()
        database = Room.inMemoryDatabaseBuilder(context, NexomonDatabase::class.java).build()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getDao() {
        val dao = database.getDao()
        assert(dao != null)
    }
}