package cn.joestar.dbcreator

import cn.joestar.database.ItemAble
import org.junit.Test
import java.io.File
import java.io.FileInputStream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataCheckTest {
    private val convertor = Convertor()

    private val root = File("../assets")

    @Test
    fun testConvertMonster() {
        val file = File(root, "monsters.json")
        assert(file.exists())
        check(file) { c, list ->
            c.convertMonster(list)
        }
    }

    @Test
    fun testConvertLocation() {
        val file = File(root, "locations.json")
        assert(file.exists())
        check(file) { c, list ->
            c.convertMonster(list)
        }
    }

    private fun check(file: File, action: (Convertor, List<String>) -> List<ItemAble>) {
        val stream = FileInputStream(file)
        val list = convertor.readJson(stream)
        checkData(action(convertor, list))
    }

    private fun checkData(list: List<ItemAble>) {
        val size = list.size
        val hashMap = HashMap<Int, ItemAble>(size)
        list.forEach {
            val key = it.provideId()
            if (hashMap.containsKey(key)) {
                throw IllegalArgumentException("Id $key has same,name is ${it.provideName()}")
            } else {
                hashMap[key] = it
            }
        }
    }
}