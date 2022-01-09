package cn.joestar.nexonom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.joestar.database.Location
import cn.joestar.nexonom.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private val stack: Stack<Entity> = Stack()
    var current by mutableStateOf<Entity>(DefaultEntity)

    fun next(index: Int) {
        when (val value = current) {
            is ListMonster -> {
                val id = value.getList()[index].monsterId
                DbRepository.setSelect(id)
            }
            is MapEntity -> {
                when (value) {
                    is Land -> toLandDetail(value, index)
                    is DetailLand -> toDetailLocal(value, index)
                    else -> {}
                }
            }
            else -> {}
        }
    }

    private fun toDetailLocal(value: DetailLand, index: Int) {
        val location = getLocationId(value, index)
        viewModelScope.launch {
            DbRepository.getDetailLocation(location.locationId).collect(::updateEntity)
        }
    }

    private fun getLocationId(
        value: ListMap,
        index: Int
    ): Location {
        return value.getList()[index]
    }

    private fun toLandDetail(value: Land, index: Int) {
        val location = getLocationId(value, index)
        viewModelScope.launch {
            DbRepository.getLocations(location.locationId)
                .map { DetailLand(location.name, it) }
                .collect(::updateEntity)
        }
    }

    private val indexMaps = 0

    //    private val indexMonsters = 1
    fun onMenuItemClick(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (index) {
                indexMaps -> {
                    DbRepository.getLands().map {
                        Land(itemLabels[index], it)
                    }
                }
                else -> {
                    DbRepository.getMonsters().map {
                        Monsters(itemLabels[index], it)
                    }
                }
            }.collect(::updateEntityByItemSelect)
        }
    }

    private fun updateEntityByItemSelect(entity: Entity) {
        stack.clear()
        stack.push(DefaultEntity)
        current = entity
    }

    private fun updateEntity(entity: Entity) {
        stack.push(current)
        current = entity
    }

    private val itemLabels = arrayOf("Maps", "Monsters")

    fun onBack(): Boolean {
        return try {
            current = stack.pop()
            true
        } catch (e: EmptyStackException) {
            false
        }
    }
}