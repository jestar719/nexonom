package cn.joestar.nexonom.entity

import cn.joestar.database.DetailLocation
import cn.joestar.database.Location
import cn.joestar.database.Monster

sealed interface Entity {
    fun getType(): EntityTyp
    fun getTitle(): String
}

sealed interface SealedMap : Entity

sealed interface SealedMonster : Entity

enum class EntityTyp {
    DEFAULT, MAP, MONSTER
}

abstract class AbsEntity(protected val text: String)

abstract class MapEntity(title: String) : AbsEntity(title), SealedMap {
    override fun getType() = EntityTyp.MAP
    override fun getTitle() = text
}

abstract class MonsterEntity(title: String) : AbsEntity(title), SealedMonster {
    override fun getType() = EntityTyp.MONSTER
    override fun getTitle() = text
}


sealed interface ListEntity

sealed interface ListMap : ListEntity {
    fun getList(): List<Location>
}

sealed interface ListMonster : ListEntity {
    fun getList(): List<Monster>
}


class Land(title: String, private val list: List<Location>) : MapEntity(title), ListMap {
    override fun getList(): List<Location> = list
}

class DetailLand(title: String, val land: Location, private val list: List<Location>) :
    MapEntity(title), ListMap {
    override fun getList(): List<Location> = list
}

class DetailLocal(title: String, private val location: DetailLocation) : MapEntity(title),
    ListMonster {
    override fun getList(): List<Monster> {
        return location.monsters
    }
}

class Monsters(title: String, private val list: List<Monster>) : MonsterEntity(title), ListMonster {
    override fun getList(): List<Monster> = list
}

object DefaultEntity : AbsEntity("Nexonom"), Entity {
    override fun getType() = EntityTyp.DEFAULT
    override fun getTitle() = text
}

