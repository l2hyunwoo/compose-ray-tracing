package io.github.l2hyunwoo.raytracer

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        state = rememberWindowState(
            size = DpSize(width = 960.dp, height = 720.dp)
        ),
        onCloseRequest = ::exitApplication,
        title = "Raytracer",
    ) {
        App()
    }
}