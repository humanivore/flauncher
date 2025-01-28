package com.example.flauncher

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flauncher.ui.theme.FlauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlauncherTheme {
                Column {
                    Surface(
                        modifier = Modifier
                            .padding(20.dp)
                            .height((LocalConfiguration.current.screenHeightDp * 0.725f).dp)
                    ) {
                        AppList(getInstalledApps())
                    }
                    Spacer(
                        modifier = Modifier.height(
                            (LocalConfiguration.current.screenHeightDp * 0.05f).dp
                        )
                    )
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

    private fun getInstalledApps(): MutableList<App> {
        val list = mutableListOf<App>()
        val appInfoList = packageManager.getInstalledPackages(0)
        appInfoList.forEach { appInfo ->
            if (appInfo!!.applicationInfo!!.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = appInfo.applicationInfo!!.loadLabel(packageManager).toString()
                val packageName = appInfo.applicationInfo!!.packageName
                val icon = appInfo.applicationInfo!!.loadIcon(packageManager)

                val app = App(appName, packageName, icon, -1)
                if (!list.contains(app)) {
                    list.add(app)
                }
            }
        }
        return list
    }
}

@Composable
fun AppListCard(app: App, modifier: Modifier = Modifier) {
    Card {
        Text(
            text = app.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AppList(appList: MutableList<App>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(appList) { app ->
            AppListCard(app)
        }
    }
}

@Preview
@Composable
fun PreviewAppList() {
    FlauncherTheme {
        AppList(SampleDockApps.mutableApps)
    }
}

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