@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.l2hyunwoo.raytracer.processing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.unit.dp
import io.github.dautovicharis.charts.LineChart
import io.github.dautovicharis.charts.model.toMultiChartDataSet
import io.github.dautovicharis.charts.style.LineChartDefaults
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
        var histogramData by remember {
            mutableStateOf<List<Pair<String, List<Int>>>>(emptyList())
        }
        val style =
            LineChartDefaults.style(
                lineAlpha = 0.7619f,
                lineColors = listOf(Color.Red, Color.Green, Color.Blue),
            )

        LaunchedEffect(brightness) {
            val pixelMap = bitmap.toPixelMap()
            pixelMap
                .buffer
                .map { Argb.from(it) * brightness }
                .forEachIndexed { index, argb -> pixelMap.buffer[index] = argb.toInt() }
            bitmap = pixelMap.toImageBitmap()
        }
        LaunchedEffect(bitmap) {
            val pixels = bitmap.toPixelMap()
                .buffer
                .map { Argb.from(it) }

            fun countToList(counts: Map<Int, Int>): List<Int> {
                return List(256) { counts.getOrDefault(it, 0) }
            }
            histogramData = listOf(
                "R" to countToList(pixels.groupingBy { it.r }.eachCount()),
                "G" to countToList(pixels.groupingBy { it.g }.eachCount()),
                "B" to countToList(pixels.groupingBy { it.b }.eachCount()),
            )
        }
        Image(
            bitmap = bitmap,
            contentDescription = "Lena",
            modifier = Modifier.weight(1f)
                .fillMaxSize()
        )
        Column(
            modifier = Modifier.width(360.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
            if (histogramData.isEmpty()) {
                CircularProgressIndicator(Modifier.size(50.dp))
            } else {
                LineChart(
                    dataSet = histogramData.toMultiChartDataSet(
                        title = "Lena Histogram",
                    ),
                    style = style
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Equalization")
                // Checkbox()
            }
        }
    }
}
