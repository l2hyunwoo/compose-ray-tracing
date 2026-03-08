@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.l2hyunwoo.raytracer.processing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.unit.dp
import io.github.l2hyunwoo.raytracer.model.Argb
import io.github.l2hyunwoo.raytracer.model.toImageBitmap
import org.jetbrains.compose.resources.imageResource
import raytracer.composeapp.generated.resources.Res
import raytracer.composeapp.generated.resources.lena

/**
 * 영상 반전
 * HDR
 * Gaussian Blur
 * Bloom Effect
 * Histogram
 */
@Composable
fun ImageProcessingScreen(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        val lena = imageResource(
            Res.drawable.lena
        )
        var bitmap by remember {
            mutableStateOf(lena)
        }
        var brightness by remember {
            mutableFloatStateOf(1.0f)
        }
        LaunchedEffect(brightness) {
            val pixelMap = lena.toPixelMap()
            pixelMap
                .buffer
                .map { Argb.from(it) * brightness }
                .forEachIndexed { index, argb -> pixelMap.buffer[index] = argb.toInt() }
            bitmap = pixelMap.toImageBitmap()
        }
        Image(
            bitmap = bitmap,
            contentDescription = "Lena",
            modifier = Modifier.weight(1f)
                .fillMaxSize()
        )
        Column(
            modifier = Modifier.width(240.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Toolbox", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Bright")
            Slider(
                value = brightness,
                onValueChange = {
                    brightness = it
                },
                valueRange = 0f..2f,
                steps = 5
            )
        }
    }
}
