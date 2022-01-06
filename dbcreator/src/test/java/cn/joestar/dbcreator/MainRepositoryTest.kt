package cn.joestar.dbcreator

import androidx.room.Room
import cn.joestar.database.NexomonDao
import cn.joestar.database.NexomonDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainRepositoryTest {

    private val context: DbContext = object : DbContext {
        val rootFile = File("../assets")
        val db by lazy {
            Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application, NexomonDatabase::class.java,
            ).build()
        }

        override fun getDao(): NexomonDao = db.getDao()

        override fun open(name: String): InputStream = FileInputStream(File(rootFile, name))
    }
    private val convertor = Convertor()

    lateinit var repository: MainRepository

    @Before
    fun setUp() {
        repository = MainRepository(convertor = convertor, context = context)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun init() {
    }

    @Test
    fun createRelations() {
        repository.createLocations(context.getDao())
    }

    @Test
    fun createLocations() {
    }

    @Test
    fun createMonsters() {
    }
}