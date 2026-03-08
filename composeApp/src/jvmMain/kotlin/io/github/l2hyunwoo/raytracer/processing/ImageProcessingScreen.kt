package io.github.l2hyunwoo.raytracer.processing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import raytracer.composeapp.generated.resources.Res
import raytracer.composeapp.generated.resources.lena

@Composable
fun ImageProcessingScreen(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.lena),
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
        }
    }
}
