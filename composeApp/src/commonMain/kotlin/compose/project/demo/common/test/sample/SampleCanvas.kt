package compose.project.demo.common.test.sample

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase

object SampleCanvas : TestCase<SampleCanvas> {

    @Composable
    override fun BoxScope.Content() {
        SpacerDraw()
    }

    @Composable
    private fun BoxScope.CanvasDraw() {
        val density = LocalDensity.current
        Canvas(modifier = Modifier) {
            drawRect(
                color = Color.Green,
                topLeft = density.run { Offset(100.dp.toPx(), 200.dp.toPx()) },
                size = density.run { Size(200.dp.toPx(), 100.dp.toPx()) },
            )
            drawLine(
                color = Color.Blue,
                start = density.run { Offset(100.dp.toPx(), 300.dp.toPx()) },
                end = density.run { Offset(300.dp.toPx(), 400.dp.toPx()) },
                strokeWidth = Stroke.DefaultMiter,
            )
        }
    }

    @Composable
    private fun BoxScope.SpacerDraw() {
        val density = LocalDensity.current
        Box(modifier = Modifier.drawWithContent {
            drawRect(
                color = Color.Green,
                topLeft = density.run { Offset(100.dp.toPx(), 100.dp.toPx()) },
                size = density.run { Size(200.dp.toPx(), 100.dp.toPx()) },
            )
            drawContent()
            drawLine(
                color = Color.Blue,
                start = density.run { Offset(100.dp.toPx(), 200.dp.toPx()) },
                end = density.run { Offset(300.dp.toPx(), 300.dp.toPx()) },
                strokeWidth = Stroke.DefaultMiter,
            )
        }) {
            Spacer(
                modifier = Modifier
                    .offset(125.dp, 150.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .background(Color.Red),
            )
        }
    }

    @Composable
    private fun BoxScope.CacheDraw() {
        val density = LocalDensity.current
        Box(modifier = Modifier.drawWithCache {
            // Here read some value from snapshot and use them in onDraw block.
            onDrawWithContent {
                drawRect(
                    color = Color.Green,
                    topLeft = density.run { Offset(100.dp.toPx(), 100.dp.toPx()) },
                    size = density.run { Size(200.dp.toPx(), 100.dp.toPx()) },
                )
                drawContent()
                drawLine(
                    color = Color.Blue,
                    start = density.run { Offset(100.dp.toPx(), 200.dp.toPx()) },
                    end = density.run { Offset(300.dp.toPx(), 300.dp.toPx()) },
                    strokeWidth = Stroke.DefaultMiter,
                )
            }
        }) {
            Spacer(
                modifier = Modifier
                    .offset(125.dp, 150.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .background(Color.Red),
            )
        }
    }
}
