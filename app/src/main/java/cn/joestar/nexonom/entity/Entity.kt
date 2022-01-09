package cn.joestar.nexonom.entity

import cn.joestar.database.Location
import cn.joestar.database.Monster

sealed interface Entity {
    fun getTitle(): String
}

sealed interface SealedMap : Entity

sealed interface SealedMonster : Entity


abstract class AbsEntity(protected val text: String) : Entity {
    override fun getTitle() = text
}

abstract class MapEntity(title: String) : AbsEntity(title), SealedMap

abstract class MonsterEntity(title: String) : AbsEntity(title), SealedMonster

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

class DetailLand(title: String, private val list: List<Location>) : MapEntity(title), ListMap {
    override fun getList(): List<Location> = list
}

class Monsters(title: String, private val list: List<Monster>) : MonsterEntity(title), ListMonster {
    override fun getList(): List<Monster> = list
}

object DefaultEntity : AbsEntity("Nexonom") {
    override fun getTitle() = text
}

