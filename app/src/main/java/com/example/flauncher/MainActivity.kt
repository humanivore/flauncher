package com.example.flauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.flauncher.ui.theme.FlauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlauncherTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(20.dp)
                            .height((LocalConfiguration.current.screenHeightDp * 0.725f).dp)
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(400.dp))
                            AppSearchButton(modifier = Modifier.height(30.dp))
                        }
                    }

                    Surface(
                        modifier = Modifier.height(
                            (LocalConfiguration.current.screenHeightDp * 0.225f).dp
                        )
                    ) {
                        PreviewDock()
                    }
                }
            }
        }
    }
}

@Composable
fun AppSearchButton(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Button(onClick = {
        val intent = Intent(ctx, SearchActivity::class.java)
        startActivity(ctx, intent, null)
    }) {
        Icon(
            Icons.Default.Search,
            "search",
        )
    }
}


//@Composable
//fun AppSearchBar() {
//    Surface {
//        Row(
//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(horizontal = 10.dp)
//        ) {
//            Icon(
//                Icons.Default.Search,
//                "search",
//            )
//            AppSearchInput()
//        }
//    }
//}

//@Preview
//@Composable
//fun PreviewAppSearchBar() {
//    FlauncherTheme {
//        AppSearchBar()
//    }
//}

@Composable
fun HomeArea(apps: List<App>) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(apps) { app ->
            HomeAppElement(app.iconRes!!, app.name)
        }
    }
}

@Composable
fun Dock(apps: List<App>) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            apps.forEach { app ->
                DockAppElement(app.iconRes!!)
            }
        }
    }
}

@Composable
fun DockAppElement(
    @DrawableRes drawable: Int,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null
        )
    }
}

@Composable
fun HomeAppElement(
    @DrawableRes drawable: Int,
    text: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun DockAppElementPreview() {
    FlauncherTheme {
        DockAppElement(
            drawable = R.drawable.sample_app
        )
    }
}

@Preview
@Composable
fun PreviewDock() {
    FlauncherTheme {
        Dock(SampleDockApps.apps)
    }
}

@Preview
@Composable
fun PreviewHomeArea() {
    FlauncherTheme {
        HomeArea(SampleDockApps.apps)
    }
}

object SampleDockApps {
    val apps = listOf(
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App1",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App2",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App3",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App4",
            icon = null
        ),
    )

    val mutableApps = mutableListOf(
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App1",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App2",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App3",
            icon = null
        ),
        App(
            iconRes = R.drawable.sample_app,
            packageName = "",
            name = "App4",
            icon = null
        ),
    )
}