package io.github.l2hyunwoo.raytracer.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    val title: String
}

@Serializable
data object Menu : Route {
    override val title = "Computer Graphics with Compose"
}

@Serializable
data object ImageProcessing : Route {
    override val title = "Image Processing"
}
