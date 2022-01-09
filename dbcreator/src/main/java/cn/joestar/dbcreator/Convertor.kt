package cn.joestar.dbcreator

import cn.joestar.database.Location
import cn.joestar.database.Monster
import cn.joestar.database.MonsterLocation
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader

class Convertor {
    fun readJson(open: InputStream): List<String> =
        Gson().fromJson<List<String>>(InputStreamReader(open), List::class.java)

    fun convertRelation(l: List<String>): List<MonsterLocation> {
        return getSequence(l).map { s ->
            val it = cutString(s)
            MonsterLocation(
                mId = it[0].toInt(),
                lId = it[1].toInt(),
                landsId = it[2].toInt()
            )
        }.toList()
    }

    fun convertLocation(l: List<String>): List<Location> {
        return getSequence(l).map { s ->
            val it = cutString(s)
            Location(
                locationId = it[0].toInt(),
                name = it[1],
                landsId = it[2].toInt(),
                lands = if (it.size > 3) it[3] else ""
            )
        }.toList()
    }

    fun convertMonster(l: List<String>): List<Monster> {
        return getSequence(l).map { s ->
            val it = cutString(s)
            Monster(
                monsterId = it[0].toInt(),
                name = it[1],
                type = it[2],
                rare = it[3],
                other = if (it.size > 4) it[4] else ""
            )
        }.toList()
    }

    private fun cutString(s: String) = s.split(",")

    private fun getSequence(list: List<String>) = list.asSequence()
}