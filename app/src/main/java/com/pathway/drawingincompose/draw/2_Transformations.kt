package com.pathway.drawingincompose.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview

// scale, translate, rotate, inset

@Composable
fun Foo() {
    Canvas(modifier = Modifier.fillMaxSize(),
        onDraw = {
            translate(left = 50F, top = 100F) {
                drawCircle(Color.Magenta, radius = 100F)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FooPreview() {
    Foo()
}