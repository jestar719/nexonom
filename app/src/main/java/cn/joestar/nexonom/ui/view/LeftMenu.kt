package cn.joestar.nexonom.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.joestar.nexonom.ui.theme.NexonomTheme

@Composable
fun LeftMenu(onItemClickListener: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val maps = "Maps"
            Text(
                text = maps,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClickListener(maps) },
                style = MenuTextStyle
            )
            Spacer(Modifier.height(8.dp))
            val monsters = "Monsters"
            Text(
                text = monsters,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClickListener(monsters) },
                style = MenuTextStyle
            )
        }
    }
}

val MenuTextStyle = TextStyle(
    color = Color.Blue, fontSize = 24.sp,
    textAlign = TextAlign.Start
)

@Preview
@Composable
fun LeftMenuPreview() {
    NexonomTheme {
        LeftMenu {
            Log.d("tag", it)
        }
    }
}