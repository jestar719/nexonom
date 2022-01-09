package cn.joestar.nexonom.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.joestar.nexonom.BuildConfig
import cn.joestar.nexonom.R
import cn.joestar.nexonom.ui.theme.NexonomTheme

@Composable
fun LeftMenu(onItemClickListener: (Int) -> Unit) {
    Surface(color = colors.primary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    color = colors.onPrimary,
                    fontSize = 32.sp
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Version ${BuildConfig.VERSION_NAME}",
                    color = colors.onPrimary,
                    fontSize = 16.sp
                )
            }
            val items = stringArrayResource(id = R.array.menus)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(items) { index, item ->
                    MenuDivider()
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(24.dp)
                            .clickable { onItemClickListener(index) },
                        color = colors.onPrimary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
fun MenuDivider() {
    Divider(color = colors.onPrimary, thickness = 1.dp)
}

@Preview
@Composable
fun LeftMenuPreview() {
    NexonomTheme {
        LeftMenu() {}
    }
}