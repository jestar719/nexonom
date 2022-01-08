package cn.joestar.nexonom.ui.view

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cn.joestar.nexonom.ui.theme.NexonomTheme
import kotlinx.coroutines.launch


@Composable
fun ScaffoldScreen(title: String) {
    var text by remember {
        mutableStateOf(title)
    }
    var isEdit by remember { mutableStateOf(true) }
    val onEditClick: () -> Unit = {
        isEdit = !isEdit
    }
    val drawerState = rememberScaffoldState()
    val corp = rememberCoroutineScope()
    Scaffold(
        scaffoldState = drawerState,
        topBar = { TitleBar(text, isEdit, onEditClick, drawerState, corp) },
        drawerContent = {
            LeftMenu {
                text = it
                corp.launch {
                    drawerState.drawerState.close()
                }
            }
        },
        content = {
            Splash()
        }
    )
}


@Preview
@Composable
fun ScaffoldScreenPreview() {
    NexonomTheme {
        ScaffoldScreen(title = "Nexonom")
    }
}
