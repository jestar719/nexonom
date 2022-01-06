package cn.joestar.database

import androidx.room.*

interface ItemAble {
    fun provideId(): Int
    fun provideName(): String
    fun getShowText(): String
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

    override fun getShowText(): String {
        return if (lands.isEmpty()) name else "$lands.$name"
    }

    override fun toString(): String {
        val o = if (lands.isEmpty()) "" else ",$lands"
        return "$locationId,$name,$landsId$o"
    }
}

@Entity
class Monster(
    @PrimaryKey val monsterId: Int,
    val name: String,
    val type: String,
    val rare: String,
    val other: String
) : IMonster {
    override fun provideId(): Int = monsterId

    override fun provideType() = type

    override fun provideRare() = rare

    override fun provideOther() = other

    override fun provideName(): String = name

    override fun getShowText(): String {
        return toString()
    }

    override fun toString(): String {
        val o = if (other.isEmpty()) "" else ",$other"
        return "$monsterId,$name,$type,$rare$o"
    }
}

@Entity(primaryKeys = ["mId", "lId"])
class MonsterLocation(val mId: Int, val lId: Int, val landsId: Int) :
    Comparable<MonsterLocation> {
    override fun toString(): String {
        return "$mId,$lId,$landsId"
    }

    override fun compareTo(other: MonsterLocation): Int {
        if (this == other)
            return 0
        var result = mId - other.mId
        if (result == 0) {
            result = landsId - other.landsId
        }
        if (result == 0) {
            result = lId - other.lId
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MonsterLocation

        if (mId != other.mId) return false
        if (lId != other.lId) return false
        if (landsId != other.landsId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mId
        result = 31 * result + lId
        result = 31 * result + landsId
        return result
    }
}


class DetailMonster(
    @Embedded
    val monster: Monster,
    @Relation(
        parentColumn = "monsterId",
        entity = Location::class,
        entityColumn = "locationId",
        associateBy = Junction(
            value = MonsterLocation::class,
            parentColumn = "mId",
            entityColumn = "lId"
        )
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
        associateBy = Junction(
            value = MonsterLocation::class,
            parentColumn = "lId",
            entityColumn = "mId"
        )
    )
    var monsters: List<Monster>
) : ILocation by location