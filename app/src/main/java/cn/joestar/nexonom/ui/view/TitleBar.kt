package cn.joestar.nexonom.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TitleBar(
    title: String,
    isEdit: Boolean,
    canEdit: Boolean,
    onEditClick: () -> Unit,
    drawerState: ScaffoldState,
    scope: CoroutineScope
) {
    val state = drawerState.drawerState
    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (state.isClosed) {
                        state.open()
                    } else {
                        state.close()
                    }
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        actions = {
            if (canEdit) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        if (isEdit) Icons.Default.Refresh else Icons.Default.Info,
                        contentDescription = "Edit", tint = Color.White
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun TitleBarPreview() {
    var isEdit by remember { mutableStateOf(false) }
    val onEditClick: () -> Unit = { isEdit = !isEdit }
    TitleBar(
        title = "Nexonom",
        isEdit = true,
        canEdit = true,
        onEditClick = onEditClick,
        drawerState = rememberScaffoldState(),
        scope = rememberCoroutineScope()
    )
}



