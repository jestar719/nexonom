package cn.joestar.nexonom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import cn.joestar.nexonom.ui.theme.NexonomTheme
import cn.joestar.nexonom.ui.view.ScaffoldScreen

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexonomTheme {
                Surface {
                    ScaffoldScreen(
                        entity = model.current,
                        onMenuItemClick = model::onMenuItemClick,
                        onItemClick = model::next
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!model.onBack()) {
            super.onBackPressed()
        }
    }
}