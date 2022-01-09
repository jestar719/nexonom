package cn.joestar.nexonom.ui.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cn.joestar.nexonom.entity.DefaultEntity
import cn.joestar.nexonom.entity.Entity
import cn.joestar.nexonom.entity.ListEntity
import cn.joestar.nexonom.ui.theme.NexonomTheme
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun ScaffoldScreen(entity: Entity, onMenuItemClick: (Int) -> Unit, onItemClick: (Int) -> Unit) {
    var isEdit by remember { mutableStateOf(true) }
    val onEditClick: () -> Unit = {
        isEdit = !isEdit
    }
    val drawerState = rememberScaffoldState()
    val corp = rememberCoroutineScope()
    Scaffold(
        scaffoldState = drawerState,
        topBar = { TitleBar(entity.getTitle(), isEdit, onEditClick, drawerState, corp) },
        drawerContent = {
            LeftMenu() {
                onMenuItemClick(it)
                corp.launch {
                    drawerState.drawerState.close()
                }
            }
        },
        content = {
            when (entity) {
                is DefaultEntity -> {
                    Splash()
                }
                is ListEntity -> {
                    ItemList(edit = isEdit, entity = entity, onItemSelect = onItemClick)
                }
                else -> {

                }
            }
        }
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ScaffoldScreenPreview() {
    NexonomTheme {
        ScaffoldScreen(DefaultEntity, {}) {
            println(it)
        }
    }
}
