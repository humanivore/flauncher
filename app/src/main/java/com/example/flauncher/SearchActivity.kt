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

class SearchActivity : ComponentActivity() {
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
                    ) {
                        AppSearch(getInstalledApps())
                    }
                }
            }
        }
    }

    private fun getInstalledApps(): MutableList<App> {
        val list = mutableListOf<App>()
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolvedInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                mainIntent,
                PackageManager.ResolveInfoFlags.of(0L)
            )
        } else {
            packageManager.queryIntentActivities(mainIntent, 0)
        }

        resolvedInfos.forEach { resolvedInfo ->
            val appName = resolvedInfo.activityInfo!!.loadLabel(packageManager).toString()
            val packageName = resolvedInfo.activityInfo!!.packageName
            val iconRes = resolvedInfo.activityInfo.icon
            val icon = resolvedInfo.activityInfo.loadIcon(packageManager)

            val app = App(appName, packageName, icon, iconRes)
            if (!list.contains(app)) {
                list.add(app)
            }
        }

        return list
    }
}


@Composable
fun AppSearch(appList: MutableList<App>) {
    var text by remember { mutableStateOf("") }
    var filteredList: List<App> by remember {  mutableStateOf (listOf()) }

    Surface {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Icon(
                    Icons.Default.Search,
                    "search",
                )
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        filteredList = appList.filter { app ->
                            app.name.contains(text, true)
                        }
                    },
                )
            }
            AppList(
                appList = filteredList,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@Composable
fun AppListCard(app: App, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val pm = LocalContext.current.packageManager
    Card(
        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp),
        onClick = {
            try {
                val i = pm
                    .getLaunchIntentForPackage(app.packageName)!!
                    .addCategory(Intent.CATEGORY_LAUNCHER)
                startActivity(ctx, i, null)
            }
            catch (_: Exception) {

            }
        }
    ) {
        Text(
            text = app.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AppList(appList: List<App>, modifier: Modifier = Modifier) {
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
