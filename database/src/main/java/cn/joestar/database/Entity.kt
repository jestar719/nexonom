package cn.joestar.database

import androidx.room.*

interface ItemAble {
    fun provideId(): Int
    fun provideName(): String
}

interface IMonster : ItemAble {
    fun provideType(): String
    fun provideRare(): String
    fun provideOther(): String
}

interface ILocation : ItemAble {
    fun provideLands(): String
    fun provideLandsId(): Int
}

@Entity
class Location(
    @PrimaryKey val locationId: Int,
    val name: String,
    val landsId: Int = 0,
    val lands: String = ""
) : ILocation {
    override fun provideLands(): String = lands

    override fun provideLandsId() = landsId

    override fun provideId() = locationId

    override fun provideName() = name

    override fun toString(): String {
        return if (lands.isEmpty()) name else "$lands.$name"
    }
}

@Entity
class Monster(
    @PrimaryKey val monsterId: Int,
    val name: String,
    val type: String,
    val rare: String,
    @ColumnInfo(defaultValue = "") val other: String = ""
) : IMonster {
    override fun provideId(): Int = monsterId

    override fun provideType() = type

    override fun provideRare() = rare

    override fun provideOther() = other

    override fun provideName(): String = name

    override fun toString(): String {
        val o = if (other.isEmpty()) "" else ",$other"
        return "$monsterId,$name,$type,$rare$o"
    }
}

@Entity(primaryKeys = ["monsterId", "locationId"])
class MonsterLocation(val monsterId: Int, val locationId: Int, val landsId: Int)


class DetailMonster(
    @Embedded
    val monster: Monster,
    @Relation(
        parentColumn = "monsterId",
        entity = Location::class,
        entityColumn = "locationId",
        associateBy = Junction(MonsterLocation::class)
    )
    var locations: List<Location>
) : IMonster by monster

class DetailLocation(
    @Embedded
    val location: Location,
    @Relation(
        parentColumn = "locationId",
        entity = Monster::class,
        entityColumn = "monsterId",
        associateBy = Junction(MonsterLocation::class)
    )
    var monsters: List<Monster>
) : ILocation by location