package io.github.l2hyunwoo.raytracer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Raytracer",
    ) {
        App()
    }
}