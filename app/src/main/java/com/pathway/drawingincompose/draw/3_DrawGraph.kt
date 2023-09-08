package com.pathway.drawingincompose.draw

import android.graphics.PointF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Graph() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Spacer(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .aspectRatio(3 / 2F)
                .drawWithCache {
                    val path = generateSmoothPath(graphData, size)
                    val filledPath = Path()
                    filledPath.addPath(path)
                    filledPath.lineTo(size.width, size.height)
                    filledPath.lineTo(0F, size.height)
                    filledPath.close()

                    val brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Green.copy(alpha = 0.4F),
                            Color.Transparent
                        )
                    )

                    onDrawBehind {
                        drawRect(Color.White, style = Stroke(2.dp.toPx()))

                        val spaceBetweenVerticalLines = size.width / 5F
                        repeat(4) { index ->
                            val startX = spaceBetweenVerticalLines * (index + 1)
                            drawLine(
                                Color.White,
                                start = Offset(x = startX, y = 0F),
                                end = Offset(x = startX, y = size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                        val horizontalLineCount = 4
                        val verticalSpaceHeight = size.height / (3 + 1)
                        repeat(horizontalLineCount) {
                            val startY = (it + 1) * verticalSpaceHeight
                            drawLine(
                                Color.White,
                                start = Offset(0F, startY),
                                end = Offset(size.width, startY),
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                        drawPath(path = path, color = Color.Green, style = Stroke(width = 2.dp.toPx()))
                        drawPath(path = filledPath, brush = brush)
                    }
                },
        )
    }
}

private fun generatePath(data: List<Int>, size: Size): Path {
    val path = Path()

    val stepSizeInXAxis = size.width / (data.size - 1)

    val minValue = data.minBy { it }
    val maxValue = data.maxBy { it }
    val tempSize = maxValue - minValue
    val stepSizeInYAxis = size.height / tempSize

    data.forEachIndexed { index, value ->
        if (index == 0) {
            path.moveTo(0F, size.height)
        } else {
            val yValue = size.height - (value - minValue) * stepSizeInYAxis
            path.lineTo(stepSizeInXAxis * index, yValue)
        }
    }
    return path
}

fun generateSmoothPath(data: List<Int>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it }
    val min = data.minBy { it } // will map to x= 0, y = height
    val range = max - min
    val heightPxPerAmount = size.height / range.toFloat()

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance - min).toFloat() *
                        heightPxPerAmount
            )
        } else {
            val balanceX = i * weekWidth
            val balanceY = size.height - (balance - min).toFloat() *
                    heightPxPerAmount
            // to do smooth curve graph - we use cubicTo, uncomment section below for non-curve
            val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
            val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
            path.cubicTo(
                controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
                balanceX, balanceY
            )

            previousBalanceX = balanceX
            previousBalanceY = balanceY
        }
    }
    return path
}

@Preview
@Composable
fun GraphPreview() {
    Graph()
}

val graphData = listOf(65631, 65931, 65851, 65931, 66484, 67684, 66684, 66984, 70600, 71600, 72600, 72526, 72976, 73589)