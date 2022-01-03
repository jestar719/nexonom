package cn.joestar.dbcreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository = MainRepository()
) : ViewModel() {

    fun initDb() {
        viewModelScope.launch {
            repository.init()
        }
    }
}