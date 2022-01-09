package cn.joestar.nexonom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onMenuItemClick(index: Int) {
        getTab(index)
    }

    private fun toDetailLocal(value: DetailLand, index: Int) {
        val location = value.getList()[index]
        viewModelScope.launch {
            DbRepository.getDetailLocation(location.locationId)
                .map {
                    DetailLocal(location.name, it)
                }.collect(::updateEntity)
        }
    }

    private fun toLandDetail(value: Land, index: Int) {
        val location = value.getList()[index]
        viewModelScope.launch {
            DbRepository.getLocations(location.locationId)
                .map {
                    DetailLand(location.name, location, it)
                }.collect(::updateEntity)
        }
    }

    private val indexMaps = 0
    private val indexMonsters = 1
    private fun getTab(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (index == indexMaps) {
                getMapList(index)
            } else if (index == indexMonsters) {
                getMonsterList(index)
            }
        }
    }

    private suspend fun getMapList(index: Int) {
        DbRepository.getLands().map {
            Land(itemLabels[index], it)
        }.collect(::updateEntityByItemSelect)
    }

    private suspend fun getMonsterList(index: Int) {
        DbRepository.getMonsters().map {
            Monsters(itemLabels[index], it)
        }.collect(::updateEntityByItemSelect)
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