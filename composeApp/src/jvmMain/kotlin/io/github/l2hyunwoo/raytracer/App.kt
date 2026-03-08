@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.l2hyunwoo.raytracer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import io.github.l2hyunwoo.raytracer.processing.ImageProcessingScreen
import io.github.l2hyunwoo.raytracer.route.ImageProcessing
import io.github.l2hyunwoo.raytracer.route.Menu
import io.github.l2hyunwoo.raytracer.route.Route

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = rememberSerializable(
            serializer = SnapshotStateListSerializer<Route>()
        ) {
            mutableStateListOf(Menu)
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(backStack.last().title)
                    },
                    navigationIcon = {
                        if (backStack.size > 1) {
                            IconButton(onClick = { backStack.removeLastOrNull() }) {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                                    contentDescription = "Back Button",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                modifier = Modifier.fillMaxSize()
                    .padding(top = 60.dp),
                entryProvider = entryProvider {
                    entry<Menu> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    backStack.add(ImageProcessing)
                                }
                            ) {
                                Text("Move To ImageProcessing")
                            }
                        }
                    }
                    entry<ImageProcessing> {
                        ImageProcessingScreen()
                    }
                }
            )
        }
    }
}
