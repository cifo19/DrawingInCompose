package com.pathway.drawingincompose.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/*
    1- Draw Circle
    2- DrawScope
    3- Canvas Composable
 */

@Composable
fun Circle() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(Color.Magenta)
            }
    )
}

@Composable
fun CircleWithCanvas() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawCircle(Color.Magenta)
    })
}

@Preview(showBackground = true)
@Composable
private fun CircleWithCanvasPreview(){
    CircleWithCanvas()
}

@Preview(showBackground = true)
@Composable
private fun CirclePreview() {
    Circle()
}