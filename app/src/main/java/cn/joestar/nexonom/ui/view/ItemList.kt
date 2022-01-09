package cn.joestar.nexonom.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.joestar.database.Location
import cn.joestar.database.Monster
import cn.joestar.nexonom.entity.ListEntity
import cn.joestar.nexonom.entity.ListMap
import cn.joestar.nexonom.entity.ListMonster
import cn.joestar.nexonom.ui.theme.*


@ExperimentalMaterialApi
@Composable
fun ItemList(edit: Boolean, entity: ListEntity, onItemSelect: (Int) -> Unit) {
    LazyColumn(
        content = {
            when (entity) {
                is ListMonster -> getMonsters(this, edit, entity.getList(), onItemSelect)
                is ListMap -> getLocations(this, entity.getList(), onItemSelect)
            }
        },
        contentPadding = PaddingValues(4.dp)
    )
}

@ExperimentalMaterialApi
fun getLocations(scope: LazyListScope, locations: List<Location>, onItemSelect: (Int) -> Unit) {
    return scope.itemsIndexed(locations) { index, item ->
        LocationItem(index, item, onItemSelect)
    }
}

fun getMonsters(
    scope: LazyListScope,
    edit: Boolean,
    monsters: List<Monster>,
    onItemSelect: (Int) -> Unit
) {
    return scope.itemsIndexed(monsters) { index, item ->
        MonsterItem(edit, index, item, onItemSelect)
    }
}

@ExperimentalMaterialApi
@Composable
fun LocationItem(index: Int, location: Location, onItemSelect: (Int) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
        elevation = 4.dp,
        onClick = { onItemSelect(index) }
    ) {
        Text(
            text = location.name,
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun MonsterItem(edit: Boolean, index: Int, item: Monster, onItemSelect: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 4.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (edit) {
                    var enable by remember {
                        mutableStateOf(item.isCollect)
                    }
                    Checkbox(
                        checked = enable,
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                        enabled = !enable,
                        onCheckedChange = {
                            enable = it
                            if (it) {
                                onItemSelect(index)
                                item.isCollect = true
                            }
                        })
                }
                Text(
                    modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                    text = item.monsterId.convertToString()
                )
            }
            Text(
                text = item.name,
                color = getColorByRare(item.rare),
                fontSize = 20.sp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = item.type,
                    color = getColorByType(item.type)
                )
                if (item.other.isNotEmpty()) {
//                    Spacer(Modifier.padding(0.dp,4.dp))
                    Text(
                        text = item.other,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

fun getColorByRare(rare: String): Color {
    return when (rare) {
        "R" -> RareR
        "S" -> RareS
        "SR" -> RareSR
        "SSR" -> RareSSR
        else -> RareN
    }
}

fun getColorByType(rare: String): Color {
    return when (rare) {
        "火" -> TypeFire
        "水" -> TypeWater
        "地" -> TypeMineral
        "风" -> TypeWind
        "草" -> TypeGrass
        "电" -> TypeElectric
        "鬼" -> TypeGhost
        "超" -> TypePsychic
        else -> TypeNormal
    }
}


fun Int.convertToString(): String {
    val ids = this.toString()
    return when (ids.length) {
        1 -> "00$ids"
        2 -> "0$ids"
        else -> ids
    }
}

@Preview
@Composable
fun MonsterItemPreview() {
    val m = Monster(
        monsterId = 11,
        name = "TestName",
        rare = "N",
        type = "电",
        other = "水龙",
        isCollect = true
    )
    NexonomTheme {
        MonsterItem(edit = true, index = 1, item = m, onItemSelect = {})
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun LocationItemPreview() {
    val location = Location(
        locationId = 1,
        name = "TestName"
    )
    NexonomTheme {
        Surface() {
            LocationItem(index = 1, location = location, onItemSelect = {})
        }
    }
}